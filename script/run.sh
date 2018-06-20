Cur_Dir=$(pwd)
echo $Cur_Dir
sh $Cur_Dir/script/pushToECR.sh
sh $Cur_Dir/script/createCluster.sh
sh $Cur_Dir/script/registerTaskDefinition.sh
sh $Cur_Dir/script/createOrUpdateService.sh
