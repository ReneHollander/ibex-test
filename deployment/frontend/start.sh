#!/bin/sh
cp -r /usr/share/nginx/html/* /usr/share/nginx/html_tmpfs/
nginx -g "daemon off;"
