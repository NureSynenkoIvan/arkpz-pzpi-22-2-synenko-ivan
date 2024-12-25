#This shuld be added to the folder with other launchers of PiFM.
if [ "$#" -ne 1 ]; then
	echo "Usage: $0 <fileName>"
	exit 1
fi

audio="$1"

frequency="93"
ps="BastionRadio"
rt="Alarm"
pi="1ABC"
pty="0"
mpx="30"
power="5"
pre="eu"

cd /home/ivan/PirateRadio/
sudo ./pifm --freq $frequency --ps $ps --rt "$rt" --audio $audio --pi $pi --pty $pty --mpx $mpx --power $power --preemph $pre
