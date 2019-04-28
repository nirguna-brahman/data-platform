#!/usr/bin/env bash

create_dataplatform() {
  {$download_dir} = $@

  if [[ -d $download_dir/data-platform ]]; then
    echo "the forlder exists"
  else
    echo "$download_dir/data-platform does not exist. Creating the folder."
    mkdir $download_dir/data-platform
  fi
}

install() {
  1
}

start() {
  1
}

stop() {
  1
}

delete() {
  1
}

restart() {
  1
}