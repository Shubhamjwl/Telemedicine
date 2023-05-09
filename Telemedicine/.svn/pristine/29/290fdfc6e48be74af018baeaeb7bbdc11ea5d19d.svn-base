provider "docker" {
  host = "tcp://172.30.154.95:2376/"

registry_auth {
    address = "172.16.11.166:37719"
    config_file = "${pathexpand("~/.docker/config.json")}"
  }
}

resource "docker_service" "uiservice" {
  name = "uiservice"

  task_spec {
    container_spec {
      image = "172.16.11.166:37719/tele_uat/telemeduiservice:${var.IMAGEVERSION}"
      env = {
        myvar = "test"
      }
      mounts {
        target    = "/etc/localtime"
        source    = "/etc/localtime"
        type      = "bind"
      }
  }
    networks     = ["host"]
  }

  update_config {
    parallelism       = 1
    delay             = "10s"
    failure_action    = "pause"
    monitor           = "5s"
    max_failure_ratio = "0.1"
    order             = "stop-first"
  }

  endpoint_spec {
    ports {
      target_port = "80"
      published_port = "80"
      publish_mode   = "host" 
      }

    ports {
      target_port = "443"
      published_port = "443"
      publish_mode   = "host"
    }
  }

  }