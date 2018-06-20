if aws ecs describe-services --service cicd-service_EN --cluster cicd-fargate-cluster | grep "MISSING"
then
  aws ecs create-service --cluster cicd-fargate-cluster --service-name cicd-service_EN --task-definition cicd-task_EN --desired-count 1 --launch-type "FARGATE" --network-configuration "awsvpcConfiguration={subnets=[subnet-9dfb2dfa,subnet-c4be6aea],securityGroups=[sg-07cb97cd2b50b2c3a],assignPublicIp=ENABLED}" --load-balancers targetGroupArn=arn:aws:elasticloadbalancing:us-east-1:995807247853:targetgroup/notknown/665267fbe910d1c0,containerName=cicd-web,containerPort=8080
elif aws ecs describe-services --service cicd-service_EN --cluster cicd-fargate-cluster | grep "INACTIVE"
then
  aws ecs create-service --cluster cicd-fargate-cluster --service-name cicd-service_EN --task-definition cicd-task_EN --desired-count 1 --launch-type "FARGATE" --network-configuration "awsvpcConfiguration={subnets=[subnet-9dfb2dfa,subnet-c4be6aea],securityGroups=[sg-07cb97cd2b50b2c3a],assignPublicIp=ENABLED}" --load-balancers targetGroupArn=arn:aws:elasticloadbalancing:us-east-1:995807247853:targetgroup/notknown/665267fbe910d1c0,containerName=cicd-web,containerPort=8080
else
  aws ecs update-service --service cicd-service_EN --task-definition cicd-task_EN --cluster cicd-fargate-cluster
fi
