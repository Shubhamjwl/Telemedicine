provider "docker" {
  host = "tcp://172.21.11.162:2376/"

registry_auth {
    address = "172.16.11.166:37719"
    config_file = "${pathexpand("~/.docker/config.json")}"
  }
}

resource "docker_service" "gatewayservice" {
  name = "gatewayservice"

  task_spec {
    container_spec {
      image = "172.16.11.166:37719/teleprod/gatewayservice:${var.Version}"
      env = {
        active_profile_env= "prod"
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
      target_port = "7084"
      published_port = "7084"

      }
  }
  }
