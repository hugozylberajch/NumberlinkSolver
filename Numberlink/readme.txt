TASK 6 : 

D'abord cr�er le Numberlink en initialisant : 
- la map = matrice qui contient 0 ou le num�ro du label 
- cr�er un objet Numberlink appell� "test" avec le constructeur qui prend en param�tre (largeur du Numberlink,Hauteur du Numberlink,Nombre de label,la map)

Pour afficher le Numberlink : 
new Image2dViewer(test.CreateNumberlink());

Initialiser le tableau flow = tableau de taille nombre de label et avec que des 0 dedans.

Pour r�soudre le Numberlink : 
test.NumberlinkSolver(test.map,test.LabelEndPosition(),test.LabelFirstPosition(),0,flow)

TASK 7 : (a)

test.CountNumberlink()

(b)

test.LessThanK(k)
