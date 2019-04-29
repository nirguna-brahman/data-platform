#!/usr/bin/env bash

create_dataplatform() {
  download_dir=$1

  if [[ -d ${download_dir}/data-platform ]]; then
    echo "The folder $download_dir/data-platform already exists. Skipping the step."
  else
    echo "$download_dir/data-platform does not exist. Creating the folder."
    mkdir ${download_dir}/data-platform
  fi
}

install() {
  app=$1

  if [[ ${app} == "all" ]]; then
    for app in ${supported_apps[*]}; do
      install $app
    done
  else {

    if [[ -d ${download_dir}/data-platform/${app} ]]; then
      echo
      echo "Skipping Installation. App already installed here: ${download_dir}/data-platform/${app}"
      echo "If you want to install a new version please remove the old one using:"
      echo "churn --action delete --app $app"
      return
    fi

    suffix=_dist_download_url
    app_url_key=${app}${suffix}
    echo ${app_url_key}
    app_url=${!app_url_key}
    echo ${app_url}
    wget ${app_url} -P ${download_dir}/data-platform
    downloaded_tar=${app_url##*/}
    tar -xzf ${download_dir}/data-platform/${downloaded_tar} -C  ${download_dir}/data-platform
    rm ${download_dir}/data-platform/${downloaded_tar}
    extracted_dirname=${downloaded_tar%.*}
    mv ${download_dir}/data-platform/${app}* ${download_dir}/data-platform/${app}
    echo "Installed $app into $download_dir/data-platform/$app"
  }

  fi
}

start() {
  if [[ ${app} == "all" ]]; then
    for app in ${supported_apps[*]}; do
      start $app
    done
  else {
    1
  }
  fi

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