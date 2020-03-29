

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/appcodes/

    [
        {
             "app": "HELLO",
            "id": 1,
            "appcode": 123
        },
        {
            "app": "HELLO1",
            "id": 2,
            "appcode": 1234
        },
        {
            "app": "HELLO2",
            "id": 3,
            "appcode": 12345
        }
    ]

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/appData/{appcode}

    [
        {
            "id": 4,
            "appcode": 1234,
            "parent_app_name": "HELLO1",
            "hostname": "lx3",
            "fqdn": "lx3.acme",
            "sat_ipv4_addr": "123.6",
            "services": null,
            "environments": null,
            "migration_status": null,
            "destination_host": null
        },
        {
            "id": 5,
            "appcode": 1234,
            "parent_app_name": "HELLO1",
            "hostname": "lx4",
            "fqdn": "lx4.acme",
            "sat_ipv4_addr": "123.6",
            "services": null,
            "environments": null,
            "migration_status": null,
            "destination_host": nullgit
        }
    ]

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/servers

    [
        {
            "id": 2,
            "hostname": "lx1"
        },
        {
            "id": 3,
            "hostname": "lx2"
        },
        {
            "id": 4,
            "hostname": "lx3"
        },
        {
            "id": 5,
            "hostname": "lx4"
        },
        {
            "id": 6,
            "hostname": "lx5"
        }
    ]

---

ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_packages/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
            "hostname": "lx1",
            "id": 1,
            "server_id": 2,
            "dest_hostname": null,
            "packages": [
              {
                  "name": "termcap",
                  "source": "rpm",
                  "epoch": 1,
                  "version": "5.5",
                  "release": "1.20060701.1",
                  "arch": "noarch"
              },
              {
                  "name": "libcap",
                  "source": "rpm",
                  "epoch": null,
                  "version": "1.10",
                  "release": "26",
                  "arch": "x86_64"
              },
              {
                  "name": "libgpg-error",
                  "source": "rpm",
                  "epoch": null,
                  "version": "1.4",
                  "release": "2",
                  "arch": "x86_64"
              },
              {
                  "name": "zip",
                  "source": "rpm",
                  "epoch": null,
                  "version": "2.31",
                  "release": "2.el5",
                  "arch": "x86_64"
              },
              {
                  "name": "libgtop2",
                  "source": "rpm",
                  "epoch": null,
                  "version": "2.14.4",
                  "release": "8.el5_4",
                  "arch": "x86_64"
              }
            ]
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "dest_hostname": null,
            "packages": null
       }
    ]

</td></tr></table>

---


ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_listeners/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
        "hostname": "lx1",
        "id": 1,
        "server_id": 2,
        "ipv4_addr": null,
        "dest_hostname": null,
        "dest_ipv4_addr": null,
        "listeners": [
            {
                "protocol": "tcp",
                "local_port": "53",
                "process": "dnsmasq",
                "pid": "2121",
                "foreign_port": "*",
                "state": "LISTEN",
                "foreign_address": "0.0.0.0",
                "local_address": "192.168.124.1"
            },
            {
                "protocol": "tcp",
                "local_port": "631",
                "process": "cupsd",
                "pid": "7591",
                "foreign_port": "*",
                "state": "LISTEN",
                "foreign_address": "0.0.0.0",
                "local_address": "127.0.0.1"
            },
            {
                "protocol": "tcp6",
                "local_port": "",
                "process": "cupsd",
                "pid": "7591",
                "foreign_port": "",
                "state": "LISTEN",
                "foreign_address": "",
                "local_address": ""
            },
            {
                "protocol": "udp",
                "local_port": "5353",
                "process": "avahi-daemon:",
                "pid": "1329",
                "foreign_port": "*",
                "state": "",
                "foreign_address": "0.0.0.0",
                "local_address": "0.0.0.0"
            }
        ],
        "dest_listeners": null
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "listeners": null,
            "dest_listeners": null
        }
    ]

</td></tr></table>

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_services/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
            "hostname": "lx1",
            "id": 1,
            "server_id": 2,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "services": [
                {
                    "source": "sysv",
                    "state": "stopped",
                    "name": "NetworkManager"
                },
                {
                    "source": "sysv",
                    "state": "running",
                    "name": "TaniumClient"
                },
                {
                    "source": "sysv",
                    "state": "running",
                    "name": "acpid"
                },
                {
                    "source": "sysv",
                    "state": "stopped",
                    "name": "anacron"
                },
                {
                    "source": "sysv",
                    "state": "running",
                    "name": "atd"
                },
                {
                    "source": "sysv",
                    "state": "running",
                    "name": "auditd"
                },
                {
                    "source": "sysv",
                    "state": "running",
                    "name": "autofs"
                },
                {
                    "source": "sysv",
                    "state": "running",
                    "name": "avagent"
                }
            ],
            "dest_services": null
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "services": null,
            "dest_services": null
        }
    ]

</td></tr></table>

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_packages/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
            "hostname": "lx1",
            "id": 1,
            "server_id": 2,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "packages": [
                {
                    "name": "termcap",
                    "source": "rpm",
                    "epoch": 1,
                    "version": "5.5",
                    "release": "1.20060701.1",
                    "arch": "noarch"
                },
                {
                    "name": "libcap",
                    "source": "rpm",
                    "epoch": null,
                    "version": "1.10",
                    "release": "26",
                    "arch": "x86_64"
                },
                {
                    "name": "libgpg-error",
                    "source": "rpm",
                    "epoch": null,
                    "version": "1.4",
                    "release": "2",
                    "arch": "x86_64"
                },
                {
                    "name": "zip",
                    "source": "rpm",
                    "epoch": null,
                    "version": "2.31",
                    "release": "2.el5",
                    "arch": "x86_64"
                },
                {
                    "name": "libgtop2",
                    "source": "rpm",
                    "epoch": null,
                    "version": "2.14.4",
                    "release": "8.el5_4",
                    "arch": "x86_64"
                }
            ],
            "dest_packages": null
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "packages": null,
            "dest_packages": null
        }
    ]

</td></tr></table>

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_processes/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
            "hostname": "lx1",
            "id": 1,
            "server_id": 2,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "processes": [
                {
                    "TTY": "TTY",
                    "CPU_Perc": "%CPU",
                    "PID": "PID",
                    "MEM_Perc": "%MEM",
                    "COMMAND": "COMMAND",
                    "USER": "USER",
                    "STAT": "STAT"
                },
                {
                    "TTY": "?",
                    "CPU_Perc": "0.0",
                    "PID": "1",
                    "MEM_Perc": "0.0",
                    "COMMAND": "/usr/lib/systemd/systemd",
                    "USER": "root",
                    "STAT": "Ss"
                },
                {
                    "TTY": "?",
                    "CPU_Perc": "0.0",
                    "PID": "2",
                    "MEM_Perc": "0.0",
                    "COMMAND": "[kthreadd]",
                    "USER": "root",
                    "STAT": "S"
                },
                {
                    "TTY": "?",
                    "CPU_Perc": "0.0",
                    "PID": "4",
                    "MEM_Perc": "0.0",
                    "COMMAND": "[kworker/0:0H]",
                    "USER": "root",
                    "STAT": "S<"
                }
            ],
            "dest_processes": null
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "processes": null,
            "dest_processes": null
        }
    ]

</td></tr></table>

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_users/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
            "hostname": "lx1",
            "id": 1,
            "server_id": 2,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "users": [
                {
                    "username": "[root",
                    "encrypted_password": "x",
                    "login_shell": "/bin/bash",
                    "home_directory": "/root",
                    "uid": "0",
                    "shadow_days_expired": "",
                    "shadow_may_change": "0",
                    "shadow_warn_user": "7",
                    "shadow_must_change": "99999",
                    "shadow_password": "$6$FQGY6n5K2xKHESw9$UBTPFpdlTKGbM/Cv.3/Ao5Npxl11Tf5Y2Sum.NDbiU0eJ1BSpDRJ8PkR89rgYy/zAEbbx4vfA1Hh9HSyisVvK1",
                    "shadow_expired_date": "",
                    "gid": "0",
                    "gecos": "root",
                    "shadow_expire": "",
                    "shadow_last_changed": ""
                },
                {
                    "username": " bin",
                    "encrypted_password": "x",
                    "login_shell": "/sbin/nologin",
                    "home_directory": "/bin",
                    "uid": "1",
                    "shadow_days_expired": "",
                    "shadow_may_change": "0",
                    "shadow_warn_user": "7",
                    "shadow_must_change": "99999",
                    "shadow_password": "*",
                    "shadow_expired_date": "",
                    "gid": "1",
                    "gecos": "bin",
                    "shadow_expire": "",
                    "shadow_last_changed": "16853"
                }
            ]
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "users": null
        }
    ]

</td></tr></table>

---

GET ht<span>tp://</span>localhost:8080/v1/ansible/web/ansible_groups/{hostname}

<table>
<tr><th>when the object exists in the db</th><th>when the object does not exist in the db</th></tr>
<tr><td>

    [
        {
            "hostname": "lx1",
            "id": 1,
            "server_id": 2,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "groups": [
                {
                    "gid": "0",
                    "password": "x",
                    "group_list": "",
                    "group_name": "[root"
                },
                {
                    "gid": "1",
                    "password": "x",
                    "group_list": "",
                    "group_name": " bin"
                }
            ]
        }
    ]

</td><td>

    [
        {
            "hostname": null,
            "id": 0,
            "server_id": 0,
            "ipv4_addr": null,
            "dest_hostname": null,
            "dest_ipv4_addr": null,
            "groups": null
        }
    ]

</td></tr></table>
