#!/bin/sh

path="$(pwd)"
init_path="roles"

# ensure our skeleton is fully up to date and on correct branch :)
cd ../ansible-canary-role-development-application-signature-skeleton
git pull origin master
git checkout master
cd $path

if ansible-galaxy init --role-skeleton=../ansible-canary-role-development-application-signature-skeleton/ --init-path=$init_path amf-application-signature-$1; then
echo "role amf-application-signature-$1" "created at" $path"/"$init_path/amf-application-signature-$1
fi
