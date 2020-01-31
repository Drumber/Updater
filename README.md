# Updater
Only GitHub is currently supported. Support for maven will be added soon.

### Usage
```
 -cv,--currentversion <arg>   current version tag
 -o,--output <arg>            output file path
 -u,--url <arg>               api url
 -w,--wait                    wait until the file is no longer used by a process
```
  
### Example usage
```
java -jar updater.jar -u https://api.github.com/repos/Drumber/RemoteLight/releases -cv pre0.2.0.6 -o RemoteLight.jar -w
```
  
### License
```
Copyright 2020, Lars O.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
