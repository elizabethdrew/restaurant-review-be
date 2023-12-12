#!/bin/bash
awslocal s3api \
create-bucket --bucket image-bucket \
--create-bucket-configuration LocationConstraint=eu-central-1 \
--region eu-central-1
