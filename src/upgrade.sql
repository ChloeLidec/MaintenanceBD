use sondage;
-- remove from QUESTIONNAIRE where idQ=5;
DELETE FROM VALPOSSIBLE WHERE idQ=5;
DELETE FROM QUESTION WHERE idQ=5;
DELETE FROM QUESTIONNAIRE WHERE idQ=5;
Insert into QUESTIONNAIRE(idQ, Titre, Etat, numC, idU, idPan) values
(5,'Des habits et des couleurs','A',15678,1,1);
insert into QUESTION(idQ,numQ,texteQ,MaxVal,idT) values
(5,1,'De quelle couleur est votre t shirt?',NULL,'u'),
(5,2,'Sur une échelle de 0 à 10 comment noteriez vous la couleur de votre t shirt?',10,'n'),
(5,3,'Sur une échelle de 0 à 10 comment noteriez vous la couleur de votre pantalon?',10,'n'),
(5,4,'Parmi ces couleurs classez en trois pour repeindre votre pantalon.',3,'c'),
(5,5,'Quelle est votre couleur préférée?',NULL,'l');
insert into VALPOSSIBLE(idQ, numQ, idV, Valeur) values
(5,1,1,'Bleu'),
(5,1,2,'Vert'),
(5,1,3,'Rouge'),
(5,1,4,'Jaune'),
(5,1,5,'Blanc'),
(5,1,6,'Noir'),
(5,1,7,'Cyan'),
(5,4,1,'Bleu'),
(5,4,2,'Vert'),
(5,4,3,'Rouge'),
(5,4,4,'Jaune'),
(5,4,5,'Blanc'),
(5,4,6,'Noir'),
(5,4,7,'Cyan');

-- delete from UTILISATEUR where idR!=3
--change idU to 3 in QUESTIONNAIRE where Etat='A'
UPDATE QUESTIONNAIRE SET idU=3 WHERE Etat='A';
DELETE FROM QUESTIONNAIRE WHERE Etat!='A';

-- on table VALPOSSIBLE change Valeur to texteVal
ALTER TABLE VALPOSSIBLE CHANGE COLUMN Valeur texteVal VARCHAR(255) NOT NULL;

CREATE TABLE ANALYSE (
  idQ Decimal(6),
  numQ Decimal(2),
  idC decimal(3),
  commentaire Text,
  PRIMARY KEY(idQ, numQ, idC)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `ANALYSE` ADD FOREIGN KEY (`idQ`,`numQ`) REFERENCES `QUESTION` (`idQ`,`numQ`);


insert into REPONDRE(idQ,numQ,idC,valeur) values 
(5,1,'H57','3'),
(5,2,'H57','0'),
(5,3,'H57','6'),
(5,4,'H57','7; 3; 1'),
(5,5,'H57','rouge'),
(5,1,'F57','3'),
(5,2,'F57','0'),
(5,3,'F57','6'),
(5,4,'F57','7; 3; 1'),
(5,5,'F57','rouge'),
(5,1,'H17','7'),
(5,2,'H17','3'),
(5,3,'H17','9'),
(5,4,'H17','4; 2; 5'),
(5,5,'H17','rouge'),
(5,1,'F43','3'),
(5,2,'F43','3'),
(5,3,'F43','6'),
(5,4,'F43','1; 4; 6'),
(5,5,'F43','bleu'),
(5,1,'H46','3'),
(5,2,'H46','5'),
(5,3,'H46','0'),
(5,4,'H46','1; 4; 7'),
(5,5,'H46','rouge'),
(5,1,'F37','7'),
(5,2,'F37','9'),
(5,3,'F37','5'),
(5,4,'F37','1; 6; 2'),
(5,5,'F37','bleu'),
(5,1,'H15','6'),
(5,2,'H15','10'),
(5,3,'H15','5'),
(5,4,'H15','7; 2; 3'),
(5,5,'H15','vert'),
(5,1,'F45','7'),
(5,2,'F45','7'),
(5,3,'F45','4'),
(5,4,'F45','6; 4; 7'),
(5,5,'F45','vert'),
(5,1,'H57','1'),
(5,2,'H57','1'),
(5,3,'H57','8'),
(5,4,'H57','4; 2; 6'),
(5,5,'H57','vert'),
(5,1,'F66','6'),
(5,2,'F66','8'),
(5,3,'F66','6'),
(5,4,'F66','7; 3; 4'),
(5,5,'F66','bleu'),
(5,1,'H66','5'),
(5,2,'H66','7'),
(5,3,'H66','3'),
(5,4,'H66','4; 2; 6'),
(5,5,'H66','bleu'),
(5,1,'F16','5'),
(5,2,'F16','7'),
(5,3,'F16','3'),
(5,4,'F16','4; 2; 6'),
(5,5,'F16','bleu'),
(5,1,'H16','4'),
(5,2,'H16','6'),
(5,3,'H16','2'),
(5,4,'H16','3; 1; 5'),
(5,5,'H16','bleu'),
(5,1,'F56','4'),
(5,2,'F56','6'),
(5,3,'F56','2'),
(5,4,'F56','3; 1; 5'),
(5,5,'F56','bleu'),
(5,1,'H56','3'),
(5,2,'H56','5'),
(5,3,'H56','1'),
(5,4,'H56','2; 4; 7'),
(5,5,'H56','bleu'),
(5,1,'F36','2'),
(5,2,'F36','4'),
(5,3,'F36','0'),
(5,4,'F36','1; 3; 6'),
(5,5,'F36','bleu'),
(5,1,'H36','1'),
(5,2,'H36','3'),
(5,3,'H36','9'),
(5,4,'H36','0; 2; 5'),
(5,5,'H36','bleu'),
(5,1,'F26','0'),
(5,2,'F26','2'),
(5,3,'F26','8'),
(5,4,'F26','9; 1; 4'),
(5,5,'F26','bleu');

insert into INTERROGER(idU,numSond,idQ) values 
(5,1,5),
(8,2,5),
(8,3,5),
(2,4,5),
(8,5,5),
(2,6,5),
(8,7,5),
(2,8,5),
(5,9,5),
(2,10,5),
(5,11,5),
(2,12,5),
(2,13,5),
(5,14,5),
(5,15,5),
(8,16,5),
(8,17,5),
(8,18,5),
(2,19,5),
(5,20,5),
(5,21,5),
(2,22,5),
(2,23,5),
(8,24,5),
(5,25,5),
(8,26,5),
(8,27,5),
(8,28,5),
(2,29,5),
(5,30,5),
(2,31,5),
(2,32,5),
(8,33,5),
(8,34,5),
(5,35,5),
(2,36,5),
(5,37,5),
(8,38,5),
(5,39,5),
(2,40,5),
(2,41,5),
(5,42,5),
(2,43,5),
(8,44,5),
(2,45,5),
(8,46,5),
(8,47,5),
(8,48,5),
(8,49,5),
(8,50,5),
(2,51,5),
(8,52,5),
(8,53,5),
(2,54,5),
(2,55,5),
(5,56,5),
(8,57,5),
(8,58,5),
(5,59,5),
(5,60,5),
(2,61,5),
(5,62,5),
(8,63,5),
(2,64,5),
(8,65,5),
(8,66,5),
(2,67,5),
(2,68,5),
(8,69,5),
(5,70,5),
(8,71,5),
(5,72,5),
(2,73,5),
(2,74,5),
(5,75,5),
(8,76,5),
(2,77,5),
(2,78,5),
(2,79,5),
(5,80,5),
(8,81,5),
(2,82,5),
(2,83,5),
(5,84,5),
(2,85,5),
(2,86,5),
(2,87,5),
(8,88,5),
(2,89,5),
(2,90,5),
(2,91,5),
(2,92,5),
(2,93,5),
(2,94,5),
(5,95,5),
(5,96,5),
(8,97,5),
(8,98,5),
(2,99,5),
(5,100,5),
(5,101,5),
(8,102,5),
(8,103,5),
(8,104,5),
(5,105,5),
(8,106,5),
(5,107,5),
(5,108,5),
(2,109,5),
(8,110,5),
(5,111,5),
(5,112,5),
(5,113,5),
(5,114,5),
(5,115,5),
(5,116,5),
(2,117,5),
(2,118,5),
(5,119,5),
(8,120,5),
(5,121,5),
(2,122,5),
(8,123,5),
(5,124,5),
(5,125,5),
(8,126,5),
(5,127,5),
(5,128,5),
(5,129,5),
(2,130,5),
(2,131,5),
(8,132,5),
(2,133,5),
(5,134,5),
(5,135,5),
(2,136,5),
(5,137,5),
(5,138,5),
(2,139,5),
(2,140,5),
(2,141,5),
(2,142,5),
(8,143,5),
(2,144,5),
(5,145,5),
(2,146,5),
(2,147,5),
(2,148,5),
(8,149,5),
(5,150,5),
(2,151,5),
(2,152,5),
(2,153,5),
(8,154,5),
(2,155,5),
(2,156,5),
(5,157,5),
(5,158,5),
(8,159,5),
(2,160,5),
(2,161,5),
(2,162,5),
(2,163,5),
(8,164,5),
(2,165,5),
(2,166,5),
(5,167,5),
(2,168,5),
(8,169,5),
(2,170,5),
(2,171,5),
(8,172,5),
(5,173,5),
(5,174,5),
(5,175,5),
(5,176,5),
(8,177,5),
(5,178,5),
(2,179,5),
(5,180,5),
(5,181,5),
(8,182,5),
(5,183,5),
(8,184,5),
(2,185,5),
(8,186,5),
(5,187,5),
(8,188,5),
(2,189,5),
(2,190,5),
(2,191,5),
(8,192,5),
(8,193,5),
(5,194,5),
(2,195,5),
(5,196,5),
(8,197,5),
(2,198,5),
(5,199,5),
(5,200,5),
(2,201,5),
(8,202,5),
(8,203,5),
(2,204,5),
(2,205,5),
(5,206,5),
(8,207,5),
(2,208,5),
(2,209,5),
(8,210,5),
(8,211,5),
(8,212,5),
(8,213,5),
(2,214,5),
(5,215,5),
(8,216,5),
(8,217,5),
(8,218,5),
(2,219,5),
(5,220,5),
(8,221,5),
(5,222,5),
(5,223,5),
(2,224,5),
(8,225,5),
(5,226,5),
(8,227,5),
(5,228,5),
(5,229,5),
(2,230,5),
(8,231,5),
(8,232,5),
(8,233,5),
(2,234,5),
(8,235,5),
(5,236,5),
(2,237,5),
(5,238,5),
(2,239,5),
(5,240,5),
(8,241,5),
(5,242,5),
(5,243,5),
(5,244,5),
(5,245,5),
(8,246,5),
(5,247,5),
(8,248,5),
(8,249,5),
(2,250,5),
(5,251,5),
(8,252,5),
(2,253,5),
(5,254,5),
(5,255,5),
(8,256,5),
(8,257,5),
(8,258,5),
(5,259,5),
(2,260,5),
(2,261,5),
(8,262,5),
(2,263,5),
(2,264,5),
(2,265,5),
(2,266,5),
(5,267,5),
(5,268,5),
(8,269,5),
(8,270,5),
(5,271,5),
(5,272,5),
(5,273,5),
(5,274,5),
(2,275,5),
(8,276,5),
(8,277,5),
(2,278,5),
(8,279,5),
(8,280,5),
(8,281,5),
(5,282,5),
(5,283,5),
(8,284,5),
(2,285,5),
(5,286,5),
(8,287,5),
(5,288,5),
(8,289,5),
(2,290,5),
(5,291,5),
(2,292,5),
(8,293,5),
(5,294,5),
(5,295,5),
(8,296,5),
(5,297,5),
(2,298,5),
(8,299,5),
(2,300,5),
(8,301,5),
(8,302,5),
(5,303,5),
(8,304,5),
(8,305,5),
(2,306,5),
(8,307,5),
(8,308,5),
(2,309,5),
(5,310,5),
(2,311,5),
(5,312,5),
(8,313,5),
(5,314,5),
(5,315,5),
(2,316,5),
(5,317,5),
(2,318,5),
(8,319,5),
(5,320,5),
(2,321,5),
(2,322,5),
(2,323,5),
(5,324,5),
(8,325,5),
(5,326,5),
(2,327,5),
(5,328,5),
(2,329,5),
(2,330,5),
(2,331,5),
(5,332,5),
(5,333,5),
(5,334,5),
(8,335,5),
(8,336,5),
(2,337,5),
(5,338,5),
(5,339,5),
(8,340,5),
(8,341,5),
(2,342,5),
(5,343,5),
(5,344,5),
(8,345,5),
(8,346,5),
(2,347,5),
(8,348,5),
(5,349,5),
(5,350,5),
(5,351,5),
(5,352,5),
(8,353,5),
(5,354,5),
(2,355,5),
(8,356,5),
(5,357,5),
(8,358,5),
(2,359,5),
(8,360,5),
(8,361,5),
(5,362,5),
(8,363,5),
(5,364,5),
(2,365,5),
(2,366,5),
(8,367,5),
(2,368,5),
(2,369,5),
(5,370,5),
(2,371,5),
(8,372,5),
(5,373,5),
(8,374,5),
(8,375,5),
(8,376,5),
(2,377,5),
(8,378,5),
(8,379,5),
(5,380,5),
(2,381,5),
(5,382,5),
(5,383,5),
(2,384,5),
(2,385,5),
(5,386,5),
(8,387,5),
(8,388,5),
(5,389,5),
(2,390,5),
(8,391,5),
(8,392,5),
(2,393,5),
(2,394,5),
(2,395,5),
(5,396,5),
(2,397,5),
(5,398,5),
(2,399,5),
(8,400,5),
(8,401,5),
(8,402,5),
(2,403,5),
(5,404,5),
(8,405,5),
(5,406,5),
(8,407,5),
(5,408,5),
(8,409,5),
(2,410,5),
(8,411,5),
(5,412,5),
(5,413,5),
(8,414,5),
(5,415,5),
(2,416,5),
(8,417,5),
(5,418,5),
(8,419,5),
(2,420,5),
(8,421,5),
(2,422,5),
(5,423,5),
(5,424,5),
(5,425,5),
(2,426,5),
(5,427,5),
(8,428,5),
(5,429,5),
(2,430,5),
(2,431,5),
(2,432,5),
(8,433,5),
(5,434,5),
(2,435,5),
(5,436,5),
(2,437,5),
(8,438,5),
(2,439,5),
(5,440,5),
(2,441,5),
(8,442,5),
(8,443,5),
(5,444,5),
(8,445,5),
(8,446,5),
(8,447,5),
(8,448,5),
(5,449,5),
(2,450,5),
(8,451,5),
(5,452,5),
(5,453,5),
(2,454,5),
(5,455,5),
(5,456,5),
(2,457,5),
(8,458,5),
(2,459,5),
(5,460,5),
(5,461,5),
(5,462,5),
(5,463,5),
(2,464,5),
(2,465,5),
(5,466,5),
(2,467,5),
(5,468,5),
(2,469,5),
(8,470,5),
(8,471,5),
(5,472,5),
(2,473,5),
(8,474,5),
(5,475,5),
(5,476,5),
(2,477,5),
(2,478,5),
(5,479,5),
(2,480,5),
(5,481,5),
(5,482,5),
(8,483,5),
(2,484,5),
(2,485,5),
(8,486,5),
(8,487,5),
(8,488,5),
(2,489,5),
(5,490,5),
(5,491,5),
(5,492,5),
(8,493,5),
(2,494,5),
(2,495,5),
(8,496,5),
(2,497,5),
(8,498,5),
(2,499,5),
(5,500,5),
(2,501,5),
(5,502,5),
(5,503,5),
(8,504,5),
(8,505,5),
(2,506,5),
(8,507,5),
(2,508,5),
(8,509,5),
(2,510,5),
(2,511,5),
(2,512,5),
(2,513,5),
(5,514,5),
(8,515,5),
(5,516,5),
(5,517,5),
(8,518,5),
(2,519,5),
(2,520,5),
(2,521,5),
(2,522,5),
(2,523,5),
(8,524,5),
(5,525,5),
(5,526,5),
(8,527,5),
(5,528,5),
(8,529,5),
(8,530,5),
(2,531,5),
(8,532,5),
(8,533,5),
(2,534,5),
(2,535,5),
(2,536,5),
(8,537,5),
(2,538,5),
(5,539,5),
(2,540,5),
(2,541,5),
(2,542,5),
(8,543,5),
(8,544,5),
(2,545,5),
(8,546,5),
(2,547,5),
(8,548,5),
(8,549,5),
(2,550,5),
(8,551,5),
(2,552,5),
(2,553,5),
(8,554,5),
(2,555,5),
(2,556,5),
(8,557,5),
(5,558,5),
(2,559,5),
(5,560,5),
(8,561,5),
(8,562,5),
(2,563,5),
(2,564,5),
(5,565,5),
(5,566,5),
(5,567,5),
(8,568,5),
(2,569,5),
(5,570,5),
(8,571,5),
(5,572,5),
(5,573,5),
(5,574,5),
(2,575,5),
(5,576,5),
(5,577,5),
(2,578,5),
(2,579,5),
(8,580,5),
(5,581,5),
(8,582,5),
(5,583,5),
(5,584,5),
(2,585,5),
(5,586,5),
(8,587,5),
(8,588,5),
(2,589,5),
(2,590,5),
(2,591,5),
(2,592,5),
(8,593,5),
(2,594,5),
(2,595,5),
(2,596,5),
(5,597,5),
(5,598,5),
(2,599,5),
(5,600,5),
(2,601,5),
(5,602,5),
(2,603,5),
(5,604,5),
(8,605,5),
(2,606,5),
(5,607,5),
(5,608,5),
(8,609,5),
(5,610,5),
(5,611,5),
(5,612,5),
(5,613,5),
(2,614,5),
(8,615,5),
(8,616,5),
(8,617,5),
(5,618,5),
(2,619,5),
(8,620,5),
(2,621,5),
(5,622,5),
(5,623,5),
(8,624,5),
(8,625,5),
(8,626,5),
(5,627,5),
(2,628,5),
(5,629,5),
(5,630,5),
(2,631,5),
(2,632,5),
(5,633,5),
(2,634,5),
(8,635,5),
(2,636,5),
(5,637,5),
(5,638,5),
(8,639,5),
(5,640,5),
(2,641,5),
(2,642,5),
(5,643,5),
(8,644,5),
(5,645,5),
(2,646,5),
(2,647,5),
(8,648,5),
(5,649,5),
(5,650,5),
(5,651,5),
(5,652,5),
(2,653,5),
(2,654,5),
(8,655,5),
(2,656,5),
(8,657,5),
(2,658,5),
(8,659,5),
(5,660,5),
(5,661,5),
(5,662,5),
(5,663,5),
(5,664,5),
(5,665,5),
(8,666,5),
(2,667,5),
(8,668,5),
(8,669,5),
(8,670,5),
(2,671,5),
(5,672,5),
(2,673,5),
(8,674,5),
(5,675,5),
(2,676,5),
(5,677,5),
(2,678,5),
(8,679,5),
(2,680,5),
(8,681,5),
(5,682,5),
(8,683,5),
(8,684,5),
(8,685,5),
(5,686,5),
(5,687,5),
(2,688,5),
(8,689,5),
(2,690,5),
(5,691,5),
(8,692,5),
(8,693,5),
(8,694,5),
(5,695,5),
(2,696,5),
(2,697,5),
(5,698,5),
(8,699,5),
(5,700,5),
(5,701,5),
(5,702,5),
(8,703,5),
(5,704,5),
(2,705,5),
(8,706,5),
(8,707,5),
(8,708,5),
(5,709,5),
(5,710,5),
(2,711,5),
(2,712,5),
(8,713,5),
(5,714,5),
(2,715,5),
(2,716,5),
(8,717,5),
(2,718,5),
(2,719,5),
(8,720,5),
(5,721,5),
(2,722,5),
(5,723,5),
(8,724,5),
(2,725,5),
(2,726,5),
(2,727,5),
(8,728,5),
(5,729,5),
(8,730,5),
(2,731,5),
(5,732,5),
(2,733,5),
(2,734,5),
(2,735,5),
(2,736,5),
(2,737,5),
(5,738,5),
(5,739,5),
(2,740,5),
(5,741,5),
(2,742,5),
(2,743,5),
(5,744,5),
(5,745,5),
(8,746,5),
(2,747,5),
(8,748,5),
(2,749,5),
(8,750,5),
(5,751,5),
(8,752,5),
(2,753,5),
(2,754,5),
(5,755,5),
(5,756,5),
(8,757,5),
(5,758,5),
(5,759,5),
(5,760,5),
(5,761,5),
(8,762,5),
(5,763,5),
(5,764,5),
(2,765,5),
(2,766,5),
(8,767,5),
(2,768,5),
(5,769,5),
(2,770,5),
(8,771,5),
(5,772,5),
(2,773,5),
(5,774,5),
(2,775,5),
(8,776,5),
(5,777,5),
(2,778,5),
(8,779,5),
(2,780,5),
(5,781,5),
(8,782,5),
(5,783,5),
(2,784,5),
(2,785,5),
(5,786,5),
(5,787,5),
(2,788,5),
(8,789,5),
(8,790,5),
(2,791,5),
(5,792,5),
(2,793,5),
(2,794,5),
(8,795,5),
(8,796,5),
(2,797,5),
(2,798,5),
(2,799,5),
(5,800,5);