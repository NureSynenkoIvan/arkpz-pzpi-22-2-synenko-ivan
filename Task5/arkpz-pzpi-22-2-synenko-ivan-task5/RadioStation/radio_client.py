from flask import Flask, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/radio', methods=['POST'])
def play_radio():
    # Extract file path or parameters from the POST request
    data = request.get_json()
    audio_file = data.get("audio_file")  # Expected JSON key: {"audio_file": "path_to_audio.wav"}

    if not audio_file:
        return jsonify({"error": "Missing 'audio_file' parameter"}), 400

    try:
        # Run the pifm-basic command in the terminal
        result = subprocess.run(
            ["sh", "pifm-arg.sh", "/home/ivan/PiFM/sounds" + audio_file],
            cwd="/home/ivan/PiFM/src/pi4",
            capture_output=True,
            text=True
        )
        print("Successfully played file: " + audio_file)
        return jsonify({"message": "Audio started successfully!"}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    # Run Flask app on port 5000
    app.run(host='0.0.0.0', port=5000)