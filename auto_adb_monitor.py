import subprocess
import time

ADB = "platform-tools\\adb.exe"

def is_device_connected():
    try:
        result = subprocess.run([ADB, "devices"], capture_output=True, text=True)
        lines = result.stdout.strip().split('\n')
        return len(lines) > 1 and 'device' in lines[1]
    except:
        return False

def setup_port_forward():
    try:
        subprocess.run([ADB, "reverse", "tcp:8000", "tcp:8000"], check=True)
        print("✓ ADB port forwarding active: tablet localhost:8000 → laptop localhost:8000")
        return True
    except:
        return False

print("Monitoring for tablet connection...")
print("Press Ctrl+C to stop\n")

was_connected = False

while True:
    try:
        connected = is_device_connected()
        
        if connected and not was_connected:
            print("Tablet connected! Setting up port forwarding...")
            if setup_port_forward():
                print("Ready! Tablet can now access backend at http://localhost:8000\n")
            was_connected = True
        elif not connected and was_connected:
            print("Tablet disconnected.\n")
            was_connected = False
        
        time.sleep(2)
    except KeyboardInterrupt:
        print("\nStopped monitoring.")
        break
