POST AnsibleData

http://localhost:8080/v1/ansible/web/servers/{hostname}/insertAnsibleData

      "ansible_product_version":"None",
      "ansible_fips":false,
      "ansible_service_mgr":"sysvinit",
      "ansible_user_id":"root",
      "ansible_user_dir":"/root",
      "ansible_userspace_bits":"64",
      "ansible_ssh_host_key_rsa_public":"AAAAB3NzaC1yc2EAAAABIwAAAQEAzWmpkc8A7Qmzwjf34pB/yffOFYl0eMKgISbjm8x+wczXhzhjYcKMMyZk+1S53+y4eBWXyxTahQHRxWB4QuXKTarNzzIhhbcZ9eK713mbNhz5FAFetaR2Y0KQJc5SMkFKtOr+OGrllEUG/gWiBcSysbqnhjap5HgQLJq2L+9wSSy/liiBlxbuwsFsmtNVSN/7/yrmsFjEJjv9b+Uv20r96gnCPPGMHpJnd2VaPpWnbm0WojAlD5/iURvpooaCIqVnSBBhQV+2u6zlGUeQBl5WCn3/9DU4cKf/cMHKBA+F8UEY/oNPtFN8bgMSJlDSEh1KfjR1aJIXl2D3OMBJALSTXw==",
      "ansible_effective_group_id":0,
      "ansible_distribution_version":"5.9",
      "ansible_domain":"acme.net",
      "ansible_date_time":{
         "weekday_number":"2",
         "iso8601_basic_short":"20170801T161608",
         "tz":"EDT",
         "weeknumber":"31",
         "hour":"16",
         "time":"16:16:08",
         "second":"08",
         "month":"08",
         "tz_offset":"-0400",
         "epoch":"1501618568",
         "iso8601_micro":"2017-08-01T20:16:08.%fZ",
         "weekday":"Tuesday",
         "iso8601_basic":"20170801T161608%f",
         "year":"2017",
         "date":"2017-08-01",
         "iso8601":"2017-08-01T20:16:08Z",
         "day":"01",
         "minute":"16"
      },
      "ansible_real_user_id":0,
      "ansible_processor_cores":1,
      "ansible_virtualization_role":"NA",
      "ansible_env":{
         "LANG":"en_US.UTF-8",
         "SHELL":"/bin/bash",
         "MAIL":"/var/mail/root",
         "SHLVL":"2",
         "SSH_TTY":"/dev/pts/1",
         "SSH_CLIENT":"10.9.207.4 59166 22",
         "PWD":"/root",
         "LOGNAME":"root",
         "USER":"root",
         "PATH":"/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin",
         "HOME":"/root",
         "SSH_CONNECTION":"10.9.207.4 59166 10.218.224.85 22",
         "_":"/usr/bin/python"
      },
      "ansible_memory_mb":{
         "real":{
            "total":6603,
            "used":6133,
            "free":470
         },
         "swap":{
            "total":1023,
            "cached":0,
            "used":0,
            "free":1023
         },
         "nocache":{
            "used":1200,
            "free":5403
         }
      },
      "ansible_bios_version":"6.00",
      "ansible_processor":[
         "GenuineIntel",
         "Intel(R) Xeon(R) CPU E5-4640 0 @ 2.40GHz"
      ],
      "ansible_real_group_id":0,
      "ansible_lo":{
         "features":{
            "generic_receive_offload":"off",
            "tx_checksumming":"off",
            "rx_checksumming":"off",
            "generic segmentation offload":"off",
            "tcp segmentation offload":"off",
            "udp fragmentation offload":"off",
            "scatter_gather":"off"
         },
         "mtu":16436,
         "device":"lo",
         "promisc":false,
         "ipv4":{
            "broadcast":"host",
            "netmask":"255.0.0.0",
            "network":"127.0.0.0",
            "address":"127.0.0.1"
         },
         "active":true,
         "type":"loopback"
      },
      "ansible_memtotal_mb":6603,
      "ansible_architecture":"x86_64",
      "ansible_default_ipv4":{
         "macaddress":"00:50:56:a8:0b:aa",
         "network":"10.218.224.0",
         "mtu":1500,
         "broadcast":"10.218.224.255",
         "alias":"eth0",
         "netmask":"255.255.255.0",
         "address":"10.218.224.85",
         "interface":"eth0",
         "type":"ether",
         "gateway":"10.218.224.1"
      },
      "ansible_swapfree_mb":1023,
      "ansible_default_ipv6":{

      },
      "ansible_user_gid":0,
      "ansible_system_vendor":"VMware, Inc.",
      "ansible_apparmor":{
         "status":"disabled"
      },
      "ansible_cmdline":{
         "quiet":true,
         "elevator":"noop",
         "rhgb":true,
         "crashkernel":"128M@16M",
         "ro":true,
         "root":"/dev/RootVG/LogVol00"
      },
      "ansible_effective_user_id":0,
      "ansible_distribution_release":"Tikanga",
      "ansible_selinux":{
         "status":"disabled"
      },
      "ansible_os_family":"RedHat",
      "ansible_userspace_architecture":"x86_64",
      "ansible_product_uuid":"564DEA75-BA3B-A837-7343-625035E637A7",
      "ansible_product_name":"VMware Virtual Platform",
      "ansible_pkg_mgr":"yum",
      "ansible_memfree_mb":470,
      "ansible_devices":{
         "fd0":{
            "scheduler_mode":"noop",
            "rotational":null,
            "vendor":null,
            "sectors":"8",
            "sas_device_handle":null,
            "sas_address":null,
            "host":"",
            "sectorsize":512,
            "removable":"1",
            "support_discard":null,
            "holders":[

            ],
            "partitions":{

            },
            "model":null,
            "size":"4.00 KB"
         },
         "sda":{
            "scheduler_mode":"noop",
            "rotational":null,
            "vendor":"VMware",
            "sectors":"62914560",
            "sas_device_handle":null,
            "sas_address":null,
            "host":"",
            "sectorsize":512,
            "removable":"0",
            "support_discard":null,
            "holders":[

            ],
            "partitions":{
               "sda2":{
                  "sectorsize":512,
                  "uuid":null,
                  "sectors":"62701695",
                  "start":"208845",
                  "holders":[
                     "dm-7",
                     "dm-6",
                     "dm-5",
                     "dm-4",
                     "dm-3",
                     "dm-2",
                     "dm-1",
                     "dm-0"
                  ],
                  "size":"29.90 GB"
               },
               "sda1":{
                  "sectorsize":512,
                  "uuid":"4118acb9-73d6-4ded-88d4-1b59ede8c08e",
                  "sectors":"208782",
                  "start":"63",
                  "holders":[

                  ],
                  "size":"101.94 MB"
               }
            },
            "model":"Virtual disk",
            "size":"30.00 GB"
         },
         "sdb":{
            "scheduler_mode":"noop",
            "rotational":null,
            "vendor":"VMware",
            "sectors":"73400320",
            "sas_device_handle":null,
            "sas_address":null,
            "host":"",
            "sectorsize":512,
            "removable":"0",
            "support_discard":null,
            "holders":[

            ],
            "partitions":{
               "sdb1":{
                  "sectorsize":512,
                  "uuid":null,
                  "sectors":"73384857",
                  "start":"63",
                  "holders":[
                     "dm-2"
                  ],
                  "size":"34.99 GB"
               }
            },
            "model":"Virtual disk",
            "size":"35.00 GB"
         },
         "sdc":{
            "scheduler_mode":"noop",
            "rotational":null,
            "vendor":"VMware",
            "sectors":"178257920",
            "sas_device_handle":null,
            "sas_address":null,
            "host":"",
            "sectorsize":512,
            "removable":"0",
            "support_discard":null,
            "holders":[

            ],
            "partitions":{
               "sdc1":{
                  "sectorsize":512,
                  "uuid":null,
                  "sectors":"178257177",
                  "start":"63",
                  "holders":[
                     "dm-2"
                  ],
                  "size":"85.00 GB"
               }
            },
            "model":"Virtual disk",
            "size":"85.00 GB"
         }
      },
      "ansible_user_uid":0,
      "ansible_lvm":{
         "lvs":{
            "patrollv":{
               "size_g":"2.00",
               "vg":"RootVG"
            },
            "homelv":{
               "size_g":"2.00",
               "vg":"RootVG"
            },
            "varlv":{
               "size_g":"4.00",
               "vg":"RootVG"
            },
            "LogVol00":{
               "size_g":"5.00",
               "vg":"RootVG"
            },
            "maestrolv":{
               "size_g":"2.00",
               "vg":"RootVG"
            },
            "swaplv":{
               "size_g":"1.00",
               "vg":"RootVG"
            },
            "marimbalv":{
               "size_g":"2.00",
               "vg":"RootVG"
            },
            "optlv":{
               "size_g":"129.81",
               "vg":"RootVG"
            }
         },
         "vgs":{
            "RootVG":{
               "free_g":"2.00",
               "size_g":"149.81",
               "num_lvs":"8",
               "num_pvs":"3"
            }
         }
      },
      "ansible_distribution":"RedHat",
      "ansible_dns":{
         "nameservers":[
            "10.9.167.218",
            "10.9.167.161",
            "10.10.61.71"
         ],
         "search":[
            "acme.net",
            "acme.com",
            "resources.acme.net",
            "alico.corp",
            "ussales.net"
         ]
      },
      "ansible_ssh_host_key_dsa_public":"AAAAB3NzaC1kc3MAAACBAIslN5ciHCwYdjV3RzN9KyUR0TogplQbWkdiS4x1+HOLV9ryg5Hn7Vhm1l7Ui2xqMxqEu6DLh52GH5fj48x8TMvThM43tli2XTEqZ2BEinxHuxtjuVe88/Ip8HfeZjw5H0FyjJV54bbHYjvTObibcP86NDFslj+w8uC4sZ92/uYjAAAAFQCQZQCQZBKItLI0lxTCDyyWsJriswAAAIAs80BKxQJXqUUpfMKLnn1f+YjiKeM9uv/G246NfKbsQw+Xdqu7Xyf9ALNqhYD39w41+r0wzm6bZZViUE9aQxoaKHpJE0tXpkwaDA9hD50uryIGrj5aCtpjwo14cO6guXyj8+gWjG9TGS5c+a51dNYGPUdKlE5y0EnFdYctjEyycQAAAIBv22/1lTM51/PthMzshCC1L4qJY+1K41k7Ng7A8hO+p0UpCaSu80Ys+vZU5P/l2JiK2GjKLzHeuJxYZdHC7mtd2bKwFppqeFpFoR0d3n2n0JSdHbarKMGBN2REmnmmx6Il1cbknkmXT2iBkAUw5zgEVjdP8EwLUPyu2Xt2bmwCiA==",
      "ansible_processor_count":1,
      "ansible_hostname":"lxrs0vilmtt01",
      "ansible_processor_vcpus":1,
      "ansible_virtualization_type":"NA",
      "ansible_swaptotal_mb":1023,
      "ansible_lsb":{
         "release":"5.9",
         "major_release":"5",
         "codename":"Tikanga",
         "id":"RedHatEnterpriseServer",
         "description":"Red Hat Enterprise Linux Server release 5.9 (Tikanga)"
      },
      "ansible_machine_id":"29e497435f6f0a3f0f506d0050a177b6",
      "ansible_bios_date":"04/14/2014",
      "ansible_all_ipv6_addresses":[

      ],
      "ansible_interfaces":[
         "lo",
         "eth0"
      ],
      "ansible_uptime_seconds":24596169,
      "ansible_machine":"x86_64",
      "ansible_kernel":"2.6.18-409.el5",
      "ansible_gather_subset":[
         "hardware",
         "virtual",
         "network"
      ],
      "ansible_user_gecos":"root",
      "ansible_python":{
         "executable":"/usr/bin/python",
         "version":{
            "micro":3,
            "major":2,
            "releaselevel":"final",
            "serial":0,
            "minor":4
         },
         "type":null,
         "has_sslcontext":false,
         "version_info":[
            2,
            4,
            3,
            "final",
            0
         ]
      },
      "ansible_processor_threads_per_core":1,
      "ansible_fqdn":"lxrs0vilmtt01.acme.net",
      "ansible_mounts":[
         {
            "uuid":"N/A",
            "size_total":5200543744,
            "mount":"/",
            "size_available":677298176,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-LogVol00",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":4160421888,
            "mount":"/var",
            "size_available":471818240,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-varlv",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":135018102784,
            "mount":"/opt",
            "size_available":0,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-optlv",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":2080194560,
            "mount":"/opt/maestro",
            "size_available":1399447552,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-maestrolv",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":2080194560,
            "mount":"/opt/Marimba",
            "size_available":1902501888,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-marimbalv",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":2080194560,
            "mount":"/home",
            "size_available":1001832448,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-homelv",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":2080194560,
            "mount":"/opt/patrol",
            "size_available":1486925824,
            "fstype":"ext3",
            "device":"/dev/mapper/RootVG-patrollv",
            "options":"rw"
         },
         {
            "uuid":"N/A",
            "size_total":103512064,
            "mount":"/boot",
            "size_available":60471296,
            "fstype":"ext3",
            "device":"/dev/sda1",
            "options":"rw"
         }
      ],
      "ansible_eth0":{
         "macaddress":"00:50:56:a8:0b:aa",
         "features":{
            "generic_receive_offload":"off",
            "tx_checksumming":"on",
            "rx_checksumming":"on",
            "generic segmentation offload":"off",
            "tcp segmentation offload":"on",
            "udp fragmentation offload":"off",
            "scatter_gather":"on"
         },
         "type":"ether",
         "pciid":"0000:00:11.0",
         "module":"e1000",
         "mtu":1500,
         "device":"eth0",
         "promisc":false,
         "ipv4":{
            "broadcast":"10.218.224.255",
            "netmask":"255.255.255.0",
            "network":"10.218.224.0",
            "address":"10.218.224.85"
         },
         "active":true,
         "speed":1000
      },
      "ansible_python_version":"2.4.3",
      "ansible_system":"Linux",
      "ansible_user_shell":"/bin/bash",
      "ansible_distribution_major_version":"5",
      "ansible_all_ipv4_addresses":[
         "10.218.224.85"
      ],
      "ansible_nodename":"lxrs0vilmtt01.acme.com"
   },
   "id":4,
   "summary_fields":{
      "host":{
         "id":44074,
         "name":"LXRS0VILMTT01",
         "description":"imported",
         "has_active_failures":false,
         "has_inventory_sources":true
      }
   },
   "services":[
      {
         "source":"sysv",
         "state":"stopped",
         "name":"NetworkManager"
      },
      {
         "source":"sysv",
         "state":"running",
         "name":"TaniumClient"
      },
      {
         "source":"sysv",
         "state":"running",
         "name":"acpid"
      },
      {
         "source":"sysv",
         "state":"stopped",
         "name":"anacron"
      },
      {
         "source":"sysv",
         "state":"running",
         "name":"atd"
      },
      {
         "source":"sysv",
         "state":"running",
         "name":"auditd"
      },
      {
         "source":"sysv",
         "state":"running",
         "name":"autofs"
      },
      {
         "source":"sysv",
         "state":"running",
         "name":"avagent"
      }
   ],
   "packages":[
      {
         "name":"termcap",
         "source":"rpm",
         "epoch":1,
         "version":"5.5",
         "release":"1.20060701.1",
         "arch":"noarch"
      },
      {
         "name":"libcap",
         "source":"rpm",
         "epoch":null,
         "version":"1.10",
         "release":"26",
         "arch":"x86_64"
      },
      {
         "name":"libgpg-error",
         "source":"rpm",
         "epoch":null,
         "version":"1.4",
         "release":"2",
         "arch":"x86_64"
      },
      {
         "name":"zip",
         "source":"rpm",
         "epoch":null,
         "version":"2.31",
         "release":"2.el5",
         "arch":"x86_64"
      },
      {
         "name":"libgtop2",
         "source":"rpm",
         "epoch":null,
         "version":"2.14.4",
         "release":"8.el5_4",
         "arch":"x86_64"
      }
   ],
   "listeners": [
    {"protocol": "tcp", "local_port": "53", "process": "dnsmasq", "pid": "2121", "foreign_port": "*", "state": "LISTEN", "foreign_address": "0.0.0.0", "local_address": "192.168.124.1"},
    {"protocol": "tcp", "local_port": "631", "process": "cupsd", "pid": "7591", "foreign_port": "*", "state": "LISTEN", "foreign_address": "0.0.0.0", "local_address": "127.0.0.1"},
    {"protocol": "tcp6", "local_port": "", "process": "cupsd", "pid": "7591", "foreign_port": "", "state": "LISTEN", "foreign_address": "", "local_address": ""},
    {"protocol": "udp", "local_port": "5353", "process": "avahi-daemon:", "pid": "1329", "foreign_port": "*", "state": "", "foreign_address": "0.0.0.0", "local_address": "0.0.0.0"}
    ],
   "repo_list": [
    { "repo_name":"Docker","repo_id":"docker-main"},
    { "repo_name":"Docker","repo_id":"docker-main-repo"},
    {"repo_name":"Docker","repo_id":"docker-testing"}
    ],
   "processes": [
    { "TTY":"TTY","CPU_Perc":"%CPU","PID":"PID", "MEM_Perc":"%MEM","COMMAND":"COMMAND","USER":"USER","STAT":"STAT"},
    {"TTY":"?", "CPU_Perc":"0.0","PID":"1", "MEM_Perc":"0.0","COMMAND":"/usr/lib/systemd/systemd","USER":"root","STAT":"Ss"},
    {"TTY":"?","CPU_Perc":"0.0","PID":"2","MEM_Perc":"0.0","COMMAND":"[kthreadd]","USER":"root","STAT":"S"},
    {"TTY":"?","CPU_Perc":"0.0", "PID":"4","MEM_Perc":"0.0","COMMAND":"[kworker/0:0H]","USER":"root","STAT":"S<"}
   ],
   "user_group": {
   	"users": [
   		 {
            "username":"[root",
            "encrypted_password":"x",
            "login_shell":"/bin/bash",
            "home_directory":"/root",
            "uid":"0",
            "shadow_days_expired":"",
            "shadow_may_change":"0",
            "shadow_warn_user":"7",
            "shadow_must_change":"99999",
            "shadow_password":"$6$FQGY6n5K2xKHESw9$UBTPFpdlTKGbM/Cv.3/Ao5Npxl11Tf5Y2Sum.NDbiU0eJ1BSpDRJ8PkR89rgYy/zAEbbx4vfA1Hh9HSyisVvK1",
            "shadow_expired_date":"",
            "gid":"0",
            "gecos":"root",
            "shadow_expire":"",
            "shadow_last_changed":""
         },
         {
            "username":" bin",
            "encrypted_password":"x",
            "login_shell":"/sbin/nologin",
            "home_directory":"/bin",
            "uid":"1",
            "shadow_days_expired":"",
            "shadow_may_change":"0",
            "shadow_warn_user":"7",
            "shadow_must_change":"99999",
            "shadow_password":"*",
            "shadow_expired_date":"",
            "gid":"1",
            "gecos":"bin",
            "shadow_expire":"",
            "shadow_last_changed":"16853"
         }
    ],
    "groups":[
         {
            "gid":"0",
            "password":"x",
            "group_list":"",
            "group_name":"[root"
         },
         {
            "gid":"1",
            "password":"x",
            "group_list":"",
            "group_name":" bin"
         }
	]
   },
        "cron_jobs": {

            "cronfiles": [

                "/etc/cron.hourly/mcelog.cron",

                "/etc/cron.daily/mlocate.cron",

                "/etc/cron.daily/rpm",

                "/etc/cron.daily/tmpwatch",

                "/etc/cron.daily/logrotate",

                "/etc/cron.daily/prelink",

                "/etc/cron.daily/makewhatis.cron",

                "/etc/cron.daily/0anacron",

                "/etc/cron.weekly/makewhatis.cron",

                "/etc/cron.weekly/0anacron",

                "/etc/cron.monthly/0anacron"

            ],

            "var_spool": [],

            "crontab": [

               {

                    "hour": "*",

                    "month": "*",

                    "command": "run-parts /etc/cron.hourly",

                    "user": "root",

                    "day": "*",

                    "minute": "01",

                    "weekday": "*"

                },

                {

                    "hour": "4",

                    "month": "*",

                    "command": "run-parts /etc/cron.daily",

                    "user": "root",

                    "day": "*",

                    "minute": "02",

                    "weekday": "*"

                },

                {

                    "hour": "4",

                    "month": "*",

                    "command": "run-parts /etc/cron.weekly",

                    "user": "root",

                    "day": "*",

                    "minute": "22",

                    "weekday": "0"

                },

                {

                    "hour": "4",

                    "month": "*",

                    "command": "run-parts /etc/cron.monthly",

                    "user": "root",

                    "day": "1",

                    "minute": "42",

                    "weekday": "*"

                },

                {

                    "hour": "0",

                    "month": "*",

                    "command": "/opt/NAI/LinuxShield/bin/nails runsched",

                    "user": "root",

                    "day": "*",

                    "minute": "0",

                    "weekday": "*"

                }

            ]

        }
}
