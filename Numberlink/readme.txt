TASK 6 : 

D'abord créer le Numberlink en initialisant : 
- la map = matrice qui contient 0 ou le numéro du label 
- créer un objet Numberlink appellé "test" avec le constructeur qui prend en paramètre (largeur du Numberlink,Hauteur du Numberlink,Nombre de label,la map)

Pour afficher le Numberlink : 
new Image2dViewer(test.CreateNumberlink());

Initialiser le tableau flow = tableau de taille nombre de label et avec que des 0 dedans.

Pour résoudre le Numberlink : 
test.NumberlinkSolver(test.map,test.LabelEndPosition(),test.LabelFirstPosition(),0,flow)

TASK 7 : (a)

test.CountNumberlink()

(b)

test.LessThanK(k)
