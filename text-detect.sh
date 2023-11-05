#!/bin/bash
 
# Connect to the EC2 instance using SSH with the provided private key and hostname
ssh -i "aws-project-101-keyPair.pem" ec2-user@ec2-54-92-138-154.compute-1.amazonaws.com <<EOF
    # Change directory to the specified path
    cd /home/ec2-user/awsproject/ec2-textRecognizer
 
    # Run the Java JAR file
    java -jar target/getstarted-1.0-SNAPSHOT-jar-with-dependencies.jar
EOF