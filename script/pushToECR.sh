if aws ecr describe-repositories | grep "demo/devops\""
then
  echo "exist repository"
else
  aws ecr create-repository --repository-name demo\/devops
fi
$(aws ecr get-login --no-include-email --region us-east-1)
docker tag demo\/devops 995807247853.dkr.ecr.us-east-1.amazonaws.com\/demo\/devops
docker push 995807247853.dkr.ecr.us-east-1.amazonaws.com\/demo\/devops
