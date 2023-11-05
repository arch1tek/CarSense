#!/bin/bash
 
# Connect to the EC2 instance using SSH with the provided private key and hostname
ssh -i "aws-project-101-keyPair.pem" ec2-user@ XXXXXXXXXX.compute-1.amazonaws.com <<EOF
    
    # Change directory to the specified path
    cd /home/ec2-user/awsproject/ec2-textRecognizer
 
    # Replace Application.properties

    echo -e "
    aws.access.key= XXXXXXXXXX
    aws.secret.key= XXXXXXXXXX
    aws.session.token= XXXXXXXXXX
    aws.queue.url= XXXXXXXXXX
    proj=proj" > src/main/resources/application.properties

    #build the application
    
    mvn clean compile assembly:single
EOF