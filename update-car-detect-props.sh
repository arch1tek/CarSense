#!/bin/bash
 
# Connect to the EC2 instance using SSH with the provided private key and hostname
ssh -i "aws-project-101-keyPair.pem" ec2-user@ec2-3-92-143-204.compute-1.amazonaws.com <<EOF
    
    # Change directory to the specified path
    cd /home/ec2-user/awsproject/ec2-carRecognizer
 
    # Replace Application.properties

    echo -e "
    aws.access.key=ASIAYD4TI75NQSVXS2EJ
    aws.secret.key=aloTX6aqNY4TDCDz/8TILiAXiVS84A4nLUmeO3aM
    aws.session.token=FwoGZXIvYXdzEC4aDDIJXWjOCwBNw3AOgiLBAQFKiqydp1rQXqsXKwEISJQ67ulYqCwdkhl+C7m801AC19mnZy/8OI0Zxgk4PrvprBIIw4ZnpFg5BQkUSArAPyEzNT/gqmKBaAGsDKmM+W2utmU8Xud2wd4OvjqNmLoeSdEASknZxcP5DVB76wQPe1zyefLGhugCrxnYBFIC27f6pU5NwqmN1J++hzFV2MjcwPdPQ7J16ELkc/p2ja1QXfbT5kKdvyMgUCrudDJnDACPFTEqDv8hUZI1Q0u8KiQBWi4o/fDeqQYyLdEygdVUqmnIHV63tzUnL8CljmJlUjd3h61CbHOpV59Xsfyw0jmqdOSdKQpS5w==
    aws.queue.url=https://sqs.us-east-1.amazonaws.com/558117748571/aws-project-101-queue.fifo
    proj=proj" > src/main/resources/application.properties

    #build the application
    
    mvn clean compile assembly:single
EOF