currentline=cat taskdefinition.json | grep "family"
family=`echo ${currentline} | cut -d"\"" -f4`
Cur_Dir=$(pwd)
aws ecs register-task-definition --cli-input-json file://$Cur_Dir/script/taskdefinition.json
