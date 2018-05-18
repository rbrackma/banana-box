## Java containers in production - Mastering the "banana box principle"

- Slides for the 50 min talk [click here](talk.pdf "Banana box principle talk Rivieradev.fr 2018")

![Scretch notes of the talk](talk-scretch-notes.jpg?raw=true) ![Screenshot of the talk](talk-welcome-slide.jpg?raw=true)

© skretch notes [https://github.com/corinnekrych](https://github.com/corinnekrych)

- This repository was used to accompany the talk with a demo for the developer conference session : [2018.rivieradev.fr/session/338](2018.rivieradev.fr/session/338) (NCE, France)

This repository allows you to quickly create a fat jar only using JDK features (no Maven, no gradle, no depedencies like Spring-Boot).
Once the jar compiled it is used to be mounted into a docker container of your choice, see [demo-source-script-talk](demo-source-script-talk).
Goal is to quickly validate JVM behavior on containers using different versions of JDK (JDK8, 9, 10, 11,...).

### Prerequisites

You need to be root on the machine or have extended sudo rights.

You need install some extra packages. On an RPM-based Linux box you can simply use :
- `sudo yum install -y docker && sudo systemctl start docker` (make sure you use docker 1.13 as in previous versions you can not start a deamon container with the `--rm` option)
- `sudo yum install -y java-1.8.0-openjdk-devel`
- `sudo yum install -y stress` Optional is the installation of command line tool stress, allowing to demo an RAM intensive application, see usage in [demo-source-script-talk](demo-source-script-talk).
- `sudo yum install -y dstat`  Optional is the installation of command line tool dstat used for resource monitoring, see usage in [demo-source-script-talk](demo-source-script-talk).

Furthermore you should disabled the swap as mentioned in the talk :
`sudo swapoff -a`

### Build and validate BananaServer.jar
This very simple BananaServer has been derived from : [https://github.com/kanedafromparisfriends/resourcesmonger](https://github.com/kanedafromparisfriends/resourcesmonger)

```
$ cd ${YOUR_GIT_CLONE}
$ cd java-application-server-fat-jar
$ ./build-and-run.sh
added manifest
adding: fr/(in = 0) (out= 0)(stored 0%)
adding: fr/rivieradev/(in = 0) (out= 0)(stored 0%)
adding: fr/rivieradev/BananaServer$KRootHandler.class(in = 4976) (out= 2644)(deflated 46%)
adding: fr/rivieradev/BananaServer.class(in = 2628) (out= 1444)(deflated 45%)
adding: fr/rivieradev/_2018/(in = 0) (out= 0)(stored 0%)
adding: fr/rivieradev/_2018/_338/(in = 0) (out= 0)(stored 0%)
adding: fr/rivieradev/_2018/_338/BananaServer$KRootHandler.class(in = 5009) (out= 2658)(deflated 46%)
adding: fr/rivieradev/_2018/_338/BananaServer.class(in = 2650) (out= 1459)(deflated 44%)
Main Class Start
```
Exit by Ctrl+C

### Link previously build BananaServer to container image & run the Banana Box container
```
$ cd ${YOUR_GIT_CLONE}
$ source demo-source-script-talk
# now type run- && use TAB for autocompletion
$ run-
```
Now you run any container that has been preconfigured (the demo scripts used during the presentation are called `run-<DEMO-ID>-x.y.z`). You will need an internet connection to pull down the base images referenced.
```
$ cd ${YOUR_GIT_CLONE}
$ source demo-source-script-talk
$ run-1-chaos-monkey-mem-consumer-app
$ run-1-openjdk8-fat-jar-no-limits-best-effort
$ run-2- ....
```

As in some cases a container is started in the background in a non-interactive way you can easily kill all containers on your box using
```
$ cd ${YOUR_GIT_CLONE}
$ source demo-source-script-talk
$ dk
```
