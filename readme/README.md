# <a name="_ea4vvutny05j"></a>Make an IAM user (skip for aws educate account)
1. Navigate to *IAM > User*
1. Assign a name as *aws-project-101-user*

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.001.png)

1. Check the above options
1. Click *Next*
1. Assign the following policies: AmazonEC2FullAccess, AmazonRekognitionFullAccess, AmazonS3ReadOnlyAccess

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.002.png)

1. Create User
1. Take a note of the credentials

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.003.png)

1. Navigate to the *Security credentials*

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.004.png)

1. Click on *Create Access Key,* select a purpose and hit next, name it *aws-project-101-access-key*

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.005.png)

1. Take a note of the keys 

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.006.png)
#
#
# <a name="_vvctrmkb6fxk"></a><a name="_k0j4hwjvch79"></a><a name="_rkthktk7j67y"></a>Make the SQS queue
1. Shift to *us-east-1* region
1. Navigate to Amazon SQS
1. Click on *Create Queue*

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.007.png)

1. Select *FIFO* type queue
1. Give a name as *aws-project-101-queue.fifo*

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.008.png)

1. Change *visibility timeout* to 3
1. Check *High throughput FIFO queue*

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.009.png)

1. Paste the ARN of the IAM user created in the previous step in the access policies to define who can read and write messages (IF USING AWS EDUCATE, LET IT BE **ONLY** **THE QUEUE OWNER**)![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.010.png)

# <a name="_pvedumpr0ua1"></a>Make the 2 EC2 instances
1. Create 2 EC2 instances one with name *aws-project-101-ec2-car* and other with *aws-project-101-ec2-text![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.011.png)*
1. Select AMI![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.012.png)
1. Select instance type![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.013.png)
1. Generate key pair, use the same for both. Move the downloaded .pem file to the projects folder![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.014.png)

# <a name="_jd5y17s5wymw"></a>Prepare the instances to run code
1. Select the instances and click on connect. select the SSH client option. Copy the example command in the end

![](Aspose.Words.344de9b3-53f5-41c4-b69d-078a83b49dc1.015.png)

1. In terminal move to the projects folder
1. Make sure the .pem file is here
1. Run the command 3
1. Then Run the copied example command

This will ssh you into the EC2 instance’s terminal

Run the following commands on both ec2 terminals (after ssh)

```
sudo yum update
```
```
sudo yum install java
```
```
sudo yum install maven
```
```
mkdir awsproject
```
```
mkdir outputfolder
```
Use the following command on local machine terminal (inside projects folder) to send the project files to the ec2 instance
```
scp -r -i aws-project-101-keyPair.pem ec2-carRecognizer ec2-user@ec2-3-92-143-204.compute-1.amazonaws.com:awsproject  
```
```
scp -r -i aws-project-101-keyPair.pem ec2-textRecognizer <ec2-user@ec2-54-92-138-154.compute-1.amazonaws.com>:awsproject
```

# <a name="_ea4vvutny05j"></a>Running the codes

In the update-car-detect-props.sh and update-text-detect-props.sh file, update the access key, secret key and session token with AWS details in AWS Academy Learner Lab

Then run the following commands to update the application properties and build the maven projects. 
```
source update-car-detect-props.sh
```
```
source update-text-detect-props.sh
```
Run the following command to turn on the text detector which listens to the sqs queue and detects texts from images

```
source text-detect.sh 
```

Run the following command to turn on the car detector which reads images from s3 and if a car is detected with 90% confidence it is pushed to the sqs queue

```
source car-detect.sh 
```

To display the output.txt file in the outputfolder directory in the EBS storage of ec2 instance having the text-detector application, run
```
source display-output.sh 
```

