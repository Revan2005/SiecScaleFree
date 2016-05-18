#!/bin/bash

cd $(dirname $0)
gnuplot s.script i.script r.script z.script sirz.script
