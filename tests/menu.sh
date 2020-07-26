#! /bin/bash
RED='\033[0:31m'
GREEN='\033[0;32m' #Green
NC='\033[0m' # No Color
clear
echo "==============================================================================="
echo "| Compile and Execute Java source files and use the pi4J class- for Testing   |"
echo "| Written for Java Open JDK11 and pi4J v 1.2 - on Linux                       |"
echo "| Assumes pi4j libraries located in /opt/pi4j/lib                             |"
echo "-------------------------------------------------------------------------------"
echo "| S. Stier - Stier Automation LLC (www.Stier-automation.com) 	7/26/2020   |"
echo "-------------------------------------------------------------------------------"
RUNANOTHER=true
while $RUNANOTHER;do
	echo "-------------------------------------------------------------------------------"
	echo " The following Java source code files (*.java)  have been found in this" 
	echo " directory:"
	echo  *.java
	echo "-------------------------------------------------------------------------------"
	echo -e " ${GREEN}LedTest${NC} - Turns on/off LED connected to GPIO 23 - used 220 Ohm resistor"
	echo -e " ${GREEN}PBTest${NC}  - Tests pushbutton connected to GPIO 16 - PB connected to 5vdc"
	echo -e " ${GREEN}RelayTest${NC} - Turns on/off Relay connected to GPIO 21"
	echo "-------------------------------------------------------------------------------"
	echo -e "Please enter the filename (case is important):"
	read FILENAME
	echo -e "you have chosen: ${GREEN}${FILENAME}${NC}"
	echo "-------------------------------------------------------------------------------"
	echo -e "Compiling Java Source file ${GREEN}${FILENAME}${NC}...."
	SOURCEFILENAME="$FILENAME.java"
	javac -cp .:classes:/opt/pi4j/lib/'*' -d . $SOURCEFILENAME
	echo "-------------------------------------------------------------------------------"
	echo -e "Executing Java bytecode class file ${GREEN}${FILENAME}${NC}...."
	java -cp .:classes:/opt/pi4j/lib/'*' $FILENAME
	echo "-------------------------------------------------------------------------------"
	# prompt the user if they want to run another
	read -n1 -p "Run Another? [y,n]" input
	if [[ $input == "y" || $input == "Y" ]]; then
		RUNANOTHER=true
	else
		RUNANOTHER=false;
	fi
done
echo -e "\n-----------------------------------------------------------------------------"
echo "Script Complete"
echo "=============================================================================="
