# Write your code here :-)
import shutil
import os
names = ["steel", "copper", "iron","diamond","gold"]
original = '/home/clivet268/Clives Mods/MachineBuiltWorld/src/main/resources/data/machinebuiltworld/recipes/iron_axle.json'
target = '/home/clivet268/Desktop/teste.json'
with open('/home/clivet268/Desktop/teste.json', 'w+') as file:
    file.close()
shutil.copyfile(original, target)
for name in names:
    print(name)
    #target = '/home/clivet268/Desktop/crusher' + openn + direction + name + '.json'
    with open('/home/clivet268/Desktop/teste.json', 'r+') as file:
        data = file.readlines()
        if(name == "steel"):
            data[12] = data[13].replace("minecraft","machinebuiltworld")

        if(name == "copper"):
            data[12] = data[13].replace("minecraft","machinebuiltworld")
        for x in range(len(data)):
            data[x]=data[x].replace("iron",name)

    with open('/home/clivet268/Clives Mods/MachineBuiltWorld/src/main/resources/data/machinebuiltworld/recipes/' + name + '_axle.json', "w") as file:
        file.writelines( data )
        file.close()
        #os.remove('/home/clivet268/Desktop/teste.json')