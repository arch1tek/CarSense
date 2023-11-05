#!/bin/bash
 
# Connect to the EC2 instance using SSH with the provided private key and hostname
ssh -i "aws-project-101-keyPair.pem" ec2-user@ec2-54-92-138-154.compute-1.amazonaws.com <<EOF
    
    # Change directory to the specified outputpath on EBS storage
    cd /home/ec2-user/outputfolder

    #display output.txt
    cat output.txt
 
    
EOF