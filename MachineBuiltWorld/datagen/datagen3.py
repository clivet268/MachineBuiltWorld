# Write your code here :-)
import shutil
import os
openness = ["open", "closed"]
names = ["steel", "copper", "iron","diamond","gold"]
directions = ["east", "west", "south", "north"]
for openn in openness:
    for direction in directions:
        original = '/home/clivet268/Desktop/eeeeeeeeee/erere/ewrwrwe/crusher'+ openn + direction + 'gold2.json'
        target = '/home/clivet268/Desktop/teste.json'
        with open('/home/clivet268/Desktop/teste.json', 'w+') as file:
            file.close()
        shutil.copyfile(original, target)
        for name in names:
            #target = '/home/clivet268/Desktop/crusher' + openn + direction + name + '.json'
            with open('/home/clivet268/Desktop/teste.json', 'r+') as file:
                data = file.readlines()
                data[4] = '     "0": "machinebuiltworld:blocks/crusher' + name + '",\n'
                data[5] = '     "particle": "machinebuiltworld:blocks/crusher' + name + '"\n'

            with open('/home/clivet268/Clives Mods/MachineBuiltWorld/src/main/resources/assets/machinebuiltworld/models/block/crusher' + openn + direction + name + '2.json', "w") as file:
                file.writelines( data )
                file.close()
            #os.remove('/home/clivet268/Desktop/teste.json')