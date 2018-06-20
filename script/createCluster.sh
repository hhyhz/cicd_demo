if aws ecs describe-clusters --cluster cicd-fargate-cluster | grep "MISSING"
then
  aws ecs create-cluster --cluster-name cicd-fargate-cluster
fi
