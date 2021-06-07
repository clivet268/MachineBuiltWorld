import shutil
import os
import urllib.request, urllib.error, urllib.parse
import webbrowser
import pyautogui
import time

webbrowser.open('https://google.com', new=1, autoraise=True)
webbrowser.open('https://www.curseforge.com/minecraft/mc-mods/machine-built-world')
pyautogui.moveTo(360, 700, duration = 6)
pyautogui.rightClick(360, 700)
pyautogui.moveRel(10, -250, duration = 1)
pyautogui.click()
time.sleep(3)
pyautogui.write('Machine_Built_World')
pyautogui.press('enter')
time.sleep(2)
pyautogui.press('enter')

time.sleep(3)

original = '/home/clivet268/Desktop/Machine_Built_World.html'
target = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World.html'

shutil.copyfile(original, target)

original = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World.html'
target = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World.txt'

shutil.copyfile(original, target)

one = 'iw'
two = 'ie'
three = 'in'
flag = 0
numb = ':('
catch = 0
with open('/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World.txt','r') as file:
    ii = 0
    iii = 0
    # reading each line
    for line in file:

        # reading each word
        for word in line.split():
            print(word)
            if flag == 0:
                if ii == 0:
                    one = word
                    print(one + " 1")
                    ii = 1
                else:
                    two = word
                    print(two + " 2")
                    ii = 0
                if two == 'Downloads</span>':
                    flag = 1
                    numb = one;
                if one == 'Downloads</span>':
                    flag = 1
                    numb = three;
                three = word

webbrowser.open('https://www.planetminecraft.com/mod/machine-built-world/')
pyautogui.moveTo(35, 700, duration = 6)
pyautogui.rightClick(35, 700)
pyautogui.moveRel(10, -250, duration = 1)
pyautogui.click()
time.sleep(3)
pyautogui.write('Machine_Built_World1')
pyautogui.press('enter')
time.sleep(2)
pyautogui.press('enter')

time.sleep(5)

original = '/home/clivet268/Desktop/Machine_Built_World1.html'
target = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World1.html'

shutil.copyfile(original, target)

original = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World1.html'
target = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World1.txt'

shutil.copyfile(original, target)

one = 'iw'
two = 'ie'
flag1 = 0
numb1 = ':('
catch = 0
with open('/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World1.txt','r') as file:
    ii = 0
    iii = 0
    # reading each line
    for line in file:

        # reading each word
        for word in line.split():
            print(word)
            if flag1 == 0:
                if ii == 0:
                    one = word
                    print(one + " 1")
                    ii = 1
                else:
                    two = word
                    print(two + " 2")
                    ii = 0
                if two == 'downloads,':
                    flag1 = 1
                    numb1 = one;
                if one == 'downloads,':
                    flag1 = 1
                    numb1 = three;
                three = word


eaio = 1
print(flag)
if flag == 1:
    print(':)')
    print(numb)
    cfd = numb[15:]
    print(cfd)
print(flag1)
if flag1 == 1:
    print(':)')
    print(numb1)
    mcpd = numb1[13:-7]
    print(mcpd)

eaio = int(cfd) + int(mcpd)
print(eaio)
if flag1 == 1:
    if flag ==1:
        text_file = open("/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/total_downloads.txt", "w")

        text_file.write(str(eaio))

        text_file.close()

original = '/home/clivet268/Clives Mods/MachineBuiltWorld/src/main/resources/META-INF/mods.toml'
target = '/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/mods.txt'

shutil.copyfile(original, target)


with open('/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/mods.txt','r') as file:
    data = file.readlines()
    data[25] = ('Downloads: ' + str(eaio) + '\n')

with open('/home/clivet268/Clives Mods/MachineBuiltWorld/src/main/resources/META-INF/mods.toml', 'w') as file:
    file.writelines( data )
    file.close()



os.remove('/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World.txt')
os.remove('/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World1.html')
os.remove('/home/clivet268/Clives Mods/MachineBuiltWorld/dgetter/Machine_Built_World1.txt')
os.remove('/home/clivet268/Desktop/Machine_Built_World1.html')
os.remove('/home/clivet268/Desktop/Machine_Built_World.html')
shutil.rmtree('/home/clivet268/Desktop/Machine_Built_World1_files')
shutil.rmtree('/home/clivet268/Desktop/Machine_Built_World_files')
os.system("pkill "+ 'firefox')