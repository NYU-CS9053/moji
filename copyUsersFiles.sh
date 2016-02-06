#this file should be run in solution_dir
while read line
do
mkdir $line
#$1 is the argument passed to the script which represents the week's number.
cp ./../$line/Spring-2016/homework/week$1/src/main/java/edu/nyu/cs9053/homework/*.java ./$line
done < users.txt
