while read line
do
mkdir $line
cd $line
#replace Fall-2015.git with appr. repository
git clone https://github.com/$line/Spring-2016.git; 
cd ..
done < users.txt
