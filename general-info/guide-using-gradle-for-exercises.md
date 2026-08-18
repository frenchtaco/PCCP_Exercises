# Mini-guide on using Gradle for exercises

We use the build tool [Gradle](https://gradle.org/) for compiling and running the code in this course.
This is because Gradle offers many advantages: easy and uniform way to import libraries, uniform compilation and execution environment in different OSs, and compatibility with popular IDEs. We will use Gradle version 9.6.1.

Furthermore, we will use Java version 25.

Below we explain how to install Gradle and the Java Development Kit (JDK), run Gradle projects and create your own Gradle project to write code from scratch. We describe the process for 3 OSs: Ubuntu 22.04, Windows 11 and MacOS. Most likely this guide applies to other versions of these OSs, and to other Linux distributions. The guide focuses on Gradle 9.6.1 and JDK 25, but the steps below should work for other versions. If you have problems following these steps do not hesitate to contact us.

## Installing Java JDK 25 and Gradle 9.6.1

1. Download and install java JDK 25 (see below, if you already have Java installed). We recommend OpenJDK (e.g., version 25 GA (build 25+36) in  [https://jdk.java.net/archive/](https://jdk.java.net/archive/)). Make sure that pick the right architecture for your hardware. The most common one is x64, but if you have an "M" chip from Apple you should choose AArch64.
   - <u>Ubuntu 22.04</u>: 
	 1. `$ sudo apt install openjdk-25-jdk`. 
   - <u>Windows 11</u>:
     1. Download zip from [https://jdk.java.net/archive/](https://jdk.java.net/archive/).
     2. Unzip in an appropriate directory. Let's denote it as `C:\path\to\jdk\`.
     3. Add to your `Path` variable the path to the `bin` folder, i.e., `C:\path\to\jdk\bin`.
		1. Open "Edit the system environment variables".
		2. Select "Environment Variables...".
        3. Double-click in the `Path` variable.
        4. Click "New".
		5. Add the path to unzipped `bin` folder, i.e., `C:\path\to\jdk\bin` (replace `C:\path\to\jdk\bin` with the actual path to the folder you unzipped). You can click on the "Browse" button to select the directory using the GUI. You may need to restart the terminal for the changes to take effect.
   - <u>MacOS</u>:
     1. Download zip from [https://jdk.java.net/archive/](https://jdk.java.net/archive/).
     2. Unzip in an appropriate directory. Let's denote it as `/path/to/jdk`
     3. Append `/Contents/Home` to the path above (i.e., `/path/to/jdk/Contents/Home`) and set this as your `JAVA_HOME` path.
		1. We explain how to set up this variable using `zsh`. 
		   Edit `$HOME/.zshrc` to include `export JAVA_HOME=/path/to/jdk/Contents/Home`. You may need to create this file if it does not exist.
		   Remember that, for changes to take effect, you should start a new terminal or run `source ~/.zshrc`.

2. Download and install Gradle 9.6.1 (see [https://docs.gradle.org/current/userguide/installation.html](https://docs.gradle.org/current/userguide/installation.html) | Installing Manually).
   
   - <u>Ubuntu 22.04</u>
	 1. Download Gradle 9.6.1 from [https://gradle.org/releases/](https://gradle.org/releases/). You may prefer to download the "binary-only" option as it takes less space.
	 2. Unzip in a directory of your choice. Let's denote it as `/path/to/gradle/`.
	 3. Set your path variable to include the `bin` directory.
		 1. Edit `$HOME/.bashrc` with `export PATH=/path/to/gradle/bin:$PATH` (replace `/path/to/gradle/bin` with the actual path to the folder on your machine).
		    Remember that, for changes to take effect, you should start a new terminal or run `source ~/.bashrc`.
   - <u>Windows 11</u>
	 1. Download Gradle 9.6.1 from [https://gradle.org/releases/](https://gradle.org/releases/). You may prefer to download the "binary-only" option as it takes less space.
	 2. Unzip in a directory of your choice.
	 3. Set your path variable to include the `bin` directory
		 1. Open "Edit the system environment variables".
		 2. Select "Environment Variables...".
		 3. Double-click in the `Path` variable.
		 4. Click "New", 
		 5. Add the path to unzipped `bin` folder. You may need to restart the terminal for the changes to take effect.
   - <u>MacOS</u>:
	 1. Download Gradle 9.6.1 from [https://gradle.org/releases/](https://gradle.org/releases/). You may prefer to download the "binary-only" option as it takes less space.
	 2. Unzip in a directory of your choice. Let's denote it as `/path/to/gradle/`.
	 3. Append `/bin` to the path above (i.e., `/path/to/gradle/bin`) and add this to your PATH.
		 1. Edit `$HOME/.zshrc` with `export PATH=/path/to/gradle/bin:$PATH` (replace `/path/to/gradle/bin` with the actual path to the folder on your machine). Remember that, for changes to take effect, you should start a new terminal or run `source ~/.bashrc`.


### `JAVA_HOME` environment variable in Ubuntu and Windows

It is also recommended to set the `JAVA_HOME` environment variable to the root of the OpenJDK 25 folder in Ubuntu and Windows. This variable is used several text editors and IDEs to find Java compilation and runtime utilities.

- <u>Ubuntu 24.04<u>
1. Determine the path to your OpenJDK installation. If you install it with `apt` (as described above), it is typically in the path `/usr/lib/jvm/<jdk_directory>`.
2. Edit `$HOME/.bashrc` with `export JAVA_HOME=/usr/lib/jvm/<jdk_directory>` (replace `/usr/lib/jvm/<jdk_directory>` with the actual path to the OpenJDK folder in your machine. You may need to restart the terminal for the changes to take effect.

- <u>Windows 11<u>
3. Add a new environment variable named `JAVA_HOME` with the path to the OpenJDK folder (e.g., `C:\path\to\jdk`)
  1. Open "Edit the system environment variables".
  2. Select "Environment Variables...".
  3. Select "New" (e.g., in the supper box so that it is only available to the current user)
  4. Set "Variable name:" as JAVA_HOME
  5. Set Variable value the path to `C:\path\to\jdk`. You can use the button "Browse Directory..." to select the path using the GUI. You may need to restart the terminal for the changes to take effect.

*You must make sure that the `bin` folder that you set up in the guide for OpenJDK is in the same directory as the specified `JAVA_HOME`. Otherwise, Gradle will warn you about this mismatch.*

## Running the exercises in Gradle

1. Clone the course repository `$ git clone https://github.itu.dk/jst/PCPP2025-public.git`
2. Navigate to the exercises folder for first week, e.g., `$ cd week01/code-exercises/week01exercises/`.
3. Execute the desired program by running `$ gradle -PmainClass=<package>.<java_class> run`, e.g., `$ gradle -PmainClass=exercises01.TestLongCounterExperiments run`.
   - Note that `<java_class>` should include a `main()` method.
   - This step implicitly compiles the code. You can compile only (without executing) by running `$ gradle compileJava`. There is no need to specify a class; this command compiles all files in `app/src/main/java`.

### Windows PowerShell

If you use Windows PowerShell to run Grade, make sure that it interprets the `Pmainclass` flag correctly by using quotation marks (`""`) around it.
For instance, to run the code for the first week above, you should modify the command above as follows:

`$ gradle "-PmainClass=exercises01.TestLongCounterExperiments" run`.

In general, for an arbitrary package and class you should run `$ gradle "-PmainClass=<package>.<java_class>" run` in Windows PowerShell.


## Create a Gradle project of your own

Sometimes you may want to start a Gradle project from scratch. To this end, we recommend following this official Gradle documentation [https://docs.gradle.org/current/samples/sample_building_java_applications.html](https://docs.gradle.org/9.6.1/userguide/part1_gradle_init_project.html).

In summary, when running `$ gradle init --type java-application --dsl groovy`, you should select:

- Enter target Java version (min: 7, default: 21): `25`
- Project name: *Any name that you like*
- Select application structure: `1. Single application project`
- Select test framework: `4. JUnit Jupiter`
- Generate build using new APIs and behavior (some features may change in the next minor release)?: `no`

### Minor differences

#### Running the project

In the official Gradle guide they suggest to run the project using `$ ./gradlew run`. This is equivalent to our command above. So you should be able to run the new project using `gradle run`.
In this newly created project, it is not necessary to specify the flag `PmainClass`. This is because the project has only one entry point in the automatically generated class.

#### Project structure

The structure of the project is slightly different than what we will publish in the Gradle projects for exercises and lectures.
We do not use the `gradle/libs.version.toml` file to specify the catalog dependencies.
We specify dependencies and their version numbers directly in `app/build.gradle`.
Generally, we will not publish the `gradle/` folder as it is unnecessary for the initial setup of the projects that we use in the course.
