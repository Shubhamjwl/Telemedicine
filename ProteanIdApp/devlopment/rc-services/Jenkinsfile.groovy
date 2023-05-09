#!/usr/bin/env groovy

node {
   stage('Remote Connect') {
       sh "ssh -o StrictHostKeyChecking=no -l mosip 172.30.151.101 && cd /opt"
   }
   
   stage('Checkout') {
       checkout scm
   }


def rcserviceversion
   stage ('create Image Version'){
   rcserviceversion = sh (
   script: "date +'%Y-%m-%d-%H.%M'",
   returnStdout: true,
      )
   }

   def dockerImage
   stage('build docker') {
       sh 'cd docker-images && docker load -i rc-certificate-signer.tar && docker load -i rc-certificate-api.tar'
   }

   stage('publish docker') {
       sh 'cd /var/ProteanId && ./script.sh'
       sh 'scp -o StrictHostKeyChecking=no docker-compose* mosip@172.30.151.101:/home/mosip'
      }
      
   stage('delete docker Image') {
       sh "docker rmi protean/rc-certificate-api"
       sh "docker rmi protean/rc-certificate-signer"
       sh "docker rmi 172.16.11.166:37719/proteanidapp/rc-certificate-signer"
       sh "docker rmi 172.16.11.166:37719/proteanidapp/rc-certificate-api"
       }

   stage('Deploy on Dev Env') {
         sshagent (credentials: ['nsdl']) {
       sh "ssh -o StrictHostKeyChecking=no -l mosip 172.30.151.101 /var/workspace/ProteanIdApp/helper/rc-service.sh"
         }
       }                 
   }

