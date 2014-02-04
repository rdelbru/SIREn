#! /bin/bash

###
### Check file headers
###

# Get the current directory
DIR=$(cd $(dirname "$0"); pwd)


# Get the files: *.java" but exclude the "package-info.java" files
FILES=$(find $DIR/../*/src/ -type f \( -name "*.java" -o -name "*.flex" ! -name "package-info.java" \))

# Replace the Copyright headers with the correct one
COPYRIGHT=$(cat $DIR/copyright | sed 's/\//\\\//g')

for file in $FILES; do
	# Find the first ending comment
	end_com=$(grep -n -m 1 "\*/$" $file | cut -f 1 -d ':')
 	# Check if the head part to the line $end_com contains Copyright
	head -n $end_com $file | grep " Copyright " > /dev/null

	if [[ $? -eq 0 ]]; then
	  # Replace the copyright header with the default one
    perl -0777 -i -pe 's/^\/\*\*(?:(?!\*\/$).)*\*\/$/'"${COPYRIGHT}"'/ism' $file
	else # Missing header, probably
    cat $DIR/copyright > $file.tmp
    cat $file >> $file.tmp
    mv $file.tmp $file
	fi
done

# Remove comments with either @project, @email and @author javadocs
for file in $FILES; do
  perl -0777 -i -pe 's/^\/\*\*(?:(?!\*\/$).)*@(project|email)(?:(?!\*\/$).)*\*\/$//ism' $file
  sed -i '/@author/d' $file
done
