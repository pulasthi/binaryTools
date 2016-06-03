This project allows users to convert binary files between byte order types. Converts binary files between Big-endian and Little-endian.

How to Compile the program.

Once you have cloned the git repo. simply execute the following command from the repository directory

mvn clean install

Running the Program

Once you compile tha program a executable jar file will be created under the target directory in the repo. then simply execute the following command to run the program

 java -jar binaryconverter-1.0-SNAPSHOT.jar <path-to-file>/inputFileName.bin <path-to-file>/outputFileName.bin little short

 Parameters
 1. The first parameter is the input file
 2. The second parameter is the ouput file
 3. Takes values little or big ( little is Little-Endian and big is Big-endian). This specifies the byte order of the input file
 4. Data type of the file. This can take the following values and corresponds to java primitives

    short
    int
    long
    double
    float
    byte