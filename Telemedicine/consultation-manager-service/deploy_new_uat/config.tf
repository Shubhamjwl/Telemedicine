provider "docker" {
  host = "tcp://172.30.154.96:2376/"

registry_auth {
    address = "172.16.11.166:37719"
    config_file = "${pathexpand("~/.docker/config.json")}"
  }
}

resource "docker_service" "consultation_manager" {
  name = "consultation_manager"

  task_spec {
    container_spec {
      image = "172.16.11.166:37719/telemed/consultemanageservice:${var.Version}"
      env = {
       active_profile_env= "new_uat"
        spring_config_url_env= "http://172.30.154.96:51000/config"
        spring_config_label_env= "1.0.0"
        }
      mounts {
        target    = "/var/telemedicine/documents"
        source    = "/app/telemedicine/documents"
        type      = "bind"      
      }
      mounts {
        target    = "/etc/TZ"
        source    = "/etc/localtime"
        type      = "bind"
      }
  }
  }

  update_config {
    parallelism       = 1
    delay             = "10s"
    failure_action    = "pause"
    monitor           = "5s"
    max_failure_ratio = "0.1"
    order             = "start-first"
  }

  

  endpoint_spec {
    ports {
      target_port = "7078"
      published_port = "7078"

      }
  }
  }
