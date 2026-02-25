$url = "https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar"
$output = "gradle\wrapper\gradle-wrapper.jar"

Write-Host "Downloading Gradle wrapper JAR..."
Invoke-WebRequest -Uri $url -OutFile $output

Write-Host "Gradle wrapper downloaded successfully!"
Write-Host ""
Write-Host "Now you can run: .\init-repo.bat"
