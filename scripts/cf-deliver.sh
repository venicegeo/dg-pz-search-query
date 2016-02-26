#!/bin/bash -ex
pushd `dirname $0` > /dev/null
base=$(pwd -P)
popd > /dev/null

# Gather some data about the repo
source $base/vars.sh

# Do we have this artifact in s3? 
[ -f $base/../piazzaSearchMetadataIngest*.jar ] || { aws s3 ls $S3URL && aws s3 cp $S3URL $base/../pz-search-metadata-ingest.jar || exit 1; }
