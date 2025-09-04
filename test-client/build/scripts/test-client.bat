@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  test-client startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and TEST_CLIENT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\test-client.jar;%APP_HOME%\lib\module-program-0.0.1.jar;%APP_HOME%\lib\netty-all-4.2.2.Final.jar;%APP_HOME%\lib\log4j-core-2.24.1.jar;%APP_HOME%\lib\log4j-api-2.24.1.jar;%APP_HOME%\lib\annotations-24.0.0.jar;%APP_HOME%\lib\gson-2.13.1.jar;%APP_HOME%\lib\api-0.0.1.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.2.2.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.2.2.Final-linux-aarch_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.2.2.Final-linux-riscv64.jar;%APP_HOME%\lib\netty-transport-native-io_uring-4.2.2.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-transport-native-io_uring-4.2.2.Final-linux-aarch_64.jar;%APP_HOME%\lib\netty-transport-native-io_uring-4.2.2.Final-linux-riscv64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.2.2.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.2.2.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-transport-classes-epoll-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-classes-io_uring-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-classes-kqueue-4.2.2.Final.jar;%APP_HOME%\lib\netty-resolver-dns-native-macos-4.2.2.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-resolver-dns-native-macos-4.2.2.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-resolver-dns-classes-macos-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-native-quic-4.2.2.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-codec-native-quic-4.2.2.Final-linux-aarch_64.jar;%APP_HOME%\lib\netty-codec-native-quic-4.2.2.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-codec-native-quic-4.2.2.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-codec-native-quic-4.2.2.Final-windows-x86_64.jar;%APP_HOME%\lib\netty-codec-classes-quic-4.2.2.Final.jar;%APP_HOME%\lib\netty-resolver-dns-4.2.2.Final.jar;%APP_HOME%\lib\netty-handler-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-dns-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-base-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-4.2.2.Final.jar;%APP_HOME%\lib\netty-buffer-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-haproxy-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-compression-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-http-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-http2-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-http3-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-memcache-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-mqtt-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-redis-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-smtp-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-stomp-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-xml-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-protobuf-4.2.2.Final.jar;%APP_HOME%\lib\netty-codec-marshalling-4.2.2.Final.jar;%APP_HOME%\lib\netty-resolver-4.2.2.Final.jar;%APP_HOME%\lib\netty-common-4.2.2.Final.jar;%APP_HOME%\lib\netty-handler-proxy-4.2.2.Final.jar;%APP_HOME%\lib\netty-handler-ssl-ocsp-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-rxtx-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-sctp-4.2.2.Final.jar;%APP_HOME%\lib\netty-transport-udt-4.2.2.Final.jar;%APP_HOME%\lib\error_prone_annotations-2.38.0.jar


@rem Execute test-client
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %TEST_CLIENT_OPTS%  -classpath "%CLASSPATH%" io.oliverj.client.Runner %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable TEST_CLIENT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%TEST_CLIENT_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
