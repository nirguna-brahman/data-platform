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

  if [[ $app == "zookeeper" ]]; then
    return
  fi

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
  echo "in start......"
  if [[ ${app} == "all" ]]; then
    for app in ${supported_apps[*]}; do
      start $app
    done
  else {
    case $app in
      kafka)       ${download_dir}/data-platform/${app}/bin/kafka-server-start.sh ${download_dir}/data-platform/${app}/config/server.properties &
                   ;;
      zookeeper)   ${download_dir}/data-platform/kafka/bin/zookeeper-server-start.sh ${download_dir}/data-platform/kafka/config/zookeeper.properties &
                   ;;
      flink)       ${download_dir}/data-platform/${app}/bin/start-cluster.sh
                   ;;
      spark)       1
                   ;;
    esac
  }
  fi

}

stop() {
  if [[ ${app} == "all" ]]; then
    for app in ${supported_apps[*]}; do
      start $app
    done
  else {
    case $app in
      kafka)       ${download_dir}/data-platform/${app}/bin/kafka-server-stop.sh
                   ;;
      zookeeper)   ${download_dir}/data-platform/kafka/bin/zookeeper-server-stop.sh
                   ;;
      flink)       ${download_dir}/data-platform/${app}/bin/stop-cluster.sh
                   ;;
      spark)       1
                   ;;
    esac
  }
  fi
}

delete() {
  1
}

restart() {
  1
}