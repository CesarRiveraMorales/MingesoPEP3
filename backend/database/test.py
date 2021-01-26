import time
import psycopg2
import json
import random
import time 
import datetime
from random import choice

###############################################################################
current_milli_time = lambda: int(round(time.time() * 1000))
###############################################################################
#formato AAAA-MM-DD
def rand_date(iniDate, endDate):

  initstp = time.mktime(datetime.datetime.strptime(iniDate, "%Y-%m-%d").timetuple())
  endtstp = time.mktime(datetime.datetime.strptime(endDate, "%Y-%m-%d").timetuple())
  newDate = random.randrange( initstp, endtstp )
  newDateTimestamp = datetime.datetime.fromtimestamp(newDate)
  
  return newDateTimestamp.strftime("%Y-%m-%d")


###############################################################################
def get_connection_db(dbName):
	return psycopg2.connect(user="userTBD", password="passTBD", host="127.0.0.1", port="5432", database=dbName)


###############################################################################

def insert_table(table,dbName):
  try:	
    connection = get_connection_db(dbName)
    cursor = connection.cursor()

    for i in range(0, len(table["record_to_insert"]) ):
        cursor.execute(table["postgres_insert_query"], table["record_to_insert"][i])

    connection.commit()
    print ("se insertaron "+ str( len(table["record_to_insert"]) ) +" registros en la tabla "+ table["tableName"] )


  except (Exception, psycopg2.Error) as error :
    if(connection):
      print("Failed to insert record into "+table["tableName"]+" table", error)

  finally:
    #closing database connection.
    if(connection):
      cursor.close()
      connection.close()
      print("PostgreSQL connection is closed")

  print ("=================================================================")
  return tables



###############################################################################
def delete_table(tableName,dbName):
  try:  
    connection = get_connection_db(dbName)
    cursor = connection.cursor()
    #borrar todo para la prueba
    cursor.execute("delete from "+tableName)
    connection.commit()

    print ("se eliminaron los datos de la tabla "+ tableName )

  except (Exception, psycopg2.Error) as error :
    if(connection):
      print("Failed delete => "+tableName+" table", error)

  finally:
    #closing database connection.
    if(connection):
      cursor.close()
      connection.close()
      print("PostgreSQL connection is closed")

  print ("=================================================================")

###############################################################################
def randName():
	nombre = choice(["Noemi", "Yesica", "Araceli", "Diego", "Raul", "Matias", "Maria", "Miguel", 
	"Albert", "Tomas", "Cesar", "Leonor" ,"Sebastian", "Benjamin", "Gabriel", "Javier" ,"Nicolas", "Alfredo", 
	"Arturo", "Daniel" ,"Luna", "Isidora", "Isabel", "Camila"])
	return nombre


###############################################################################
def randLastName():
	apellido = choice([" Jiménez", " Cifuentes", " Peralta", " Lima", " Duran", " Nuñez", " Ruz", " Morales", " Santana", " Acevedo", " Matamala", " Torres" ," Quezada", " Lopez", " Santillana", " Ibarra" ," Flores", " Quintana", " Sech",
	" Poblete" ," Vega", " Chateau", " Rueda", " Sanchez"])
	return apellido


###############################################################################
def getInstitucion(n):
	switcher={
		0: "Ayudemos Chile",
        1: "Tierra Austral",
        2: "Chile Primero",
        3: "Bosque a Salvo",
        4: "Fauna Alerta",
        5: "Greenpeace"
        }
	return switcher.get(n,"ZUG")

###############################################################################
def getEmergencia(n):
	switcher={
		0: "Sequia en Zona Norte",
        1: "Inundaciones Zona Sur",
        2: "Incendios Zona Sur",
        3: "Terremoto grado 10 Zona Norte",
        4: "Temporal Climatico Zona Sur"
        }
	return switcher.get(n,"ZUG")





###############################################################################
def getEmeDesc(n):
	switcher={
		0: "Aumentan las zonas de sequia extrema en la zona norte del pais, se requiere asistencia inmediata",
        1: "Niveles de agua nunca antes vistos en la zona sur comprometen la seguridad de todos",
        2: "Incendio a gran escala afecta a un gran numero de personas y bosques en general",
        3: "Terremoto de gran intensidad derrumba comunidades y destruye zonas",
        4: "Incesantes lluvias afectan la zona sur del pais, dejando comunidades aisladas y en peligro"
        }
	return switcher.get(n,"ZUG")

###############################################################################
def getTarea(n):
	switcher={
		0: "Rescate",
        1: "Transporte general",
        2: "Transporte de suministros",
        3: "Cuidador",
        4: "Organizador",
        5: "Conductor",
		6: "Abastecedor",
		7: "Reconocimiento",
		8: "Liderazgo",
		9: "Soporte"
        }
	return switcher.get(n,"ZUG")

###############################################################################
def getTareaDesc(n):
	switcher={
		0: "Lograr ingresar a la zona de catastrofe y socorrer cualquier entidad en peligro",
        1: "Proveer movilidad tanto a los voluntarios como a las entidades en peligro",
        2: "Proveer movilidad a los recursos a utilizar durante la catastrofe",
        3: "Velar por la seguridad de las entidades en peligro una vez rescatadas",
        4: "Voluntarios principales en la organizacion durante una emergencia",
        5: "Encargado de manejar vehiculos de transporte",
		6: "Encargado de proveer recursos de forma anexa a la emergencia",
		7: "Evaluar las condiciones del lugar antes de dar acceso a los rescatistas",
		8: "Liderar y organizar un grupo de no mas de 5-6 personas durante la emergencia",
		9: "Personas subordinadas a los cuidadores, facilitan acceso de recursos a los voluntarios"
        }
	return switcher.get(n,"ZUG")

###############################################################################
def getEstado():
	estado = choice(["Creada", "Iniciada", "Cancelada", "Finalizada"])
	return estado


###############################################################################
def getHab(n):
	switcher={
		0: "Comprometido",
        1: "Disciplinado",
        2: "Conocimiento en primeros auxilios",
        3: "Buen estado fisico",
        4: "Proactivo",
        5: "Responsable",
		6: "Bueno trabajando en equipo",
		7: "Comunicativo",
		8: "Posee auto disponible",
		9: "Afinidad con los animales",
		10: "Disponibilidad en los fines de semanas"
        }
	return switcher.get(n,"ZUG")

###############################################################################
def getDesc(n):
	switcher={
		0: "Organizacion no gubernamental dedicada a socorrer personas en situaciones de catastrofe",
        1: "Organizacion sin fines de lucro dedicada a proteccion de la flora sureña",
        2: "Sociedad presente en el cuidado y soporte de Chilenos en momentos de emergencia",
        3: "Comunidad de voluntarios dispuestos a promover, cuidar y mantener los bosques",
        4: "Organizacion dedicada a la proteccion de fauna en situaciones de catastrofe",
        5: "Nuestro objetivo es proteger y defender el medio ambiente y la paz, interviniendo en diferentes puntos del planeta en los que se cometen atentados contra la naturaleza."
        }
	return switcher.get(n,"ZUG")

###############################################################################
def create_simple_insert_instruction(obj):
	
	columnName = ""
	columnValues = ""
	for col in obj["columns"]:
		columnName =  columnName + col + ","
		columnValues =  columnValues + "%s,"

	postgres_insert_query = "INSERT INTO "+obj["tableName"]+" ("+columnName[:(len(columnName)-1)]+") VALUES ("+columnValues[:(len(columnValues)-1)]+")"
	return postgres_insert_query

###############################################################################
def create_simple_record(obj,tablesId,tables):
	
	record_to_insert=[]
	for id in range(0, obj["amount"]):
		record=[]
		for i in range(0, len(obj["columns"])):
			ctype = obj["columnsType"][i] 
			col = obj["columns"][i]

			if(ctype=="txt"):	
				record.append( get_column_value(ctype,obj["data"][col],str(id) ) )
			elif(ctype=="dep"):
				tableName = obj["data"][col]["tableName"]
				record.append( get_column_value(ctype,obj["data"][col], tables[ tablesId[tableName] ]["record_to_insert"]) )
			else:
				record.append( get_column_value(ctype,obj["data"][col], id) )	

		record_to_insert.append(record)	

	return record_to_insert


###############################################################################
def get_column_value(ctype,currentValue,auxValue):
	if(ctype=="txt"):		
		return currentValue.replace("N",auxValue)
	if(ctype=="rank"):		
		return currentValue.replace("RA","0")
	elif(ctype=="tar"):
		return currentValue.replace("T", getTarea(auxValue))
	elif(ctype=="tarDesc"):
		return currentValue.replace("TD", getTareaDesc(auxValue))
	elif(ctype=="inst"):
		return currentValue.replace("I", getInstitucion(auxValue))
	elif(ctype=="eme"):
		return currentValue.replace("E", getEmergencia(auxValue))
	elif(ctype=="emedesc"):
		return currentValue.replace("ED", getEmeDesc(auxValue))
	elif(ctype=="desc"):
		return currentValue.replace("D", getDesc(auxValue))
	elif(ctype=="hab"):
		return currentValue.replace("H", getHab(auxValue))
	elif(ctype=="esta"):
		return currentValue.replace("Z", getEstado())
	elif(ctype=="name"):
		return currentValue.replace("R", randName()+randLastName()+randLastName())
	elif(ctype=="date"):
		return rand_date(currentValue[0],currentValue[1])
	elif(ctype=="rnd"):
		return random.randrange(currentValue[0],currentValue[1])
	elif(ctype=="dep"):
		tableDep = auxValue
		idx = random.randrange(0,len(tableDep))			
		return auxValue[idx][ currentValue["columnId"] ]
	else:
		return "EMPTY"


###############################################################################
def create_simple_insert(tablesId,tables,tableName):
	tables[ tablesId[tableName] ]["postgres_insert_query"] = create_simple_insert_instruction(tables[ tablesId[tableName] ] )
	tables[ tablesId[tableName] ]["record_to_insert"] = create_simple_record(tables[ tablesId[tableName] ],tablesId,tables)
	return tables[ tablesId[tableName] ]


###############################################################################
def main(tables,dbName,swDelete,swInsert):

	tablesId={}
	for i in range(len(tables)):
		 tablesId[ tables[i]["tableName"] ] = i

	#para ejecutar el main de esta forma, debe respetar las dependencias de las tablas
	#es decir, las tablas sin dependencias van primero en el arreglo tablas, y a medida
	#luego van las tablas con dependencias de tablas ya creadas (existentes en el arreglo)
	for table in tables:
		tableName = table["tableName"]
		tables[ tablesId[tableName] ] = create_simple_insert(tablesId,tables,tableName)

	#en este punto ya contamos con todas las tablas y los registros a crear por tabla
	#print(json.dumps(tables, indent=2, sort_keys=True))  

	if(swDelete=='y'):
		for i in reversed(range(len(tables))):
			delete_table(tables[i]["tableName"],dbName)

	if(swInsert=='y'):
		for table in tables:
			insert_table(table,dbName)




###############################################################################

tables = [
	{	    
	"tableName":"institucion",
	"amount" : 6 ,
    "columns": ["id","nombre","descrip"],
    "columnsType": ["txt","inst","desc"],
    "data" : {"id":"N","nombre":"I" ,"descrip":"D"}
	},
	{	    
	"tableName":"voluntario",
	"amount" : 1000 ,
    "columns": ["id","nombre","fnacimiento"],
    "columnsType": ["txt","name","date"],
    "data" : { "id":"N","nombre":"R" ,"fnacimiento":["1970-01-01","2002-01-01"] }
	},
	{	    
	"tableName":"habilidad",
	"amount" : 11 ,
    "columns": ["id","descrip"],
    "columnsType": ["txt","hab"],
    "data" : {"id":"N","descrip":"H" }
	},
	{	    
	"tableName":"estado_tarea",
	"amount" : 3 ,
    "columns": ["id","descrip"],
    "columnsType": ["txt","txt"],
    "data" : {"id":"N","descrip":"estado_N" }
	},	
	{	    
	"tableName":"emergencia",
	"amount" : 5,
    "columns": ["id","nombre","descrip","finicio","ffin","id_institucion", "estado"],
    "columnsType": ["txt","eme","emedesc","date","date","dep", "esta"],
    "data" : {"id":"N", "nombre":"E","descrip":"ED","finicio":["1990-01-01","2000-01-01"],"ffin":["2000-01-01","2020-01-01"],
    		"id_institucion":{"tableName":"institucion","columnId":0}, "estado":"Z"
    	},
	},	
	{	    
	"tableName":"eme_habilidad",
	"amount" : 20,
    "columns": ["id","id_emergencia","id_habilidad"],
    "columnsType": ["txt","dep","dep"],
    "data" : {"id":"N", "id_emergencia":{"tableName":"emergencia","columnId":0}, "id_habilidad":{"tableName":"habilidad","columnId":0}},
	},	
	{	    
	"tableName":"vol_habilidad",
	"amount" : 20000,
    "columns": ["id","id_voluntario","id_habilidad"],
    "columnsType": ["txt","dep","dep"],
    "data" : {"id":"N", "id_voluntario":{"tableName":"voluntario","columnId":0}, "id_habilidad":{"tableName":"habilidad","columnId":0}},
	},
	{	    
	"tableName":"tarea",
	"amount" : 10,
    "columns": ["id","nombre","descrip","cant_vol_requeridos","cant_vol_inscritos","id_emergencia","finicio","ffin","id_estado"],
    "columnsType": ["txt","tar","tarDesc","rnd","rnd","dep","date","date","dep"],
    "data" : {
    	"id":"N",
    	"nombre":"T",
    	"descrip":"TD", 
    	"cant_vol_requeridos":[16,20],
    	"cant_vol_inscritos":[10,16],
    	"id_emergencia":{"tableName":"emergencia","columnId":0}, 
    	"finicio":["1990-01-01","2000-01-01"],
    	"ffin":["2000-01-01","2020-01-01"],
    	"id_estado":{"tableName":"estado_tarea","columnId":0}
    	}
	},
	{	    
	"tableName":"tarea_habilidad",
	"amount" : 200,
    "columns": ["id","id_emehab","id_tarea"],
    "columnsType": ["txt","dep","dep"],
    "data" : {
    	"id":"N",
    	"id_emehab":{"tableName":"eme_habilidad","columnId":0}, 
    	"id_tarea":{"tableName":"tarea","columnId":0}
    	}
	},
	{	    
	"tableName":"ranking",
	"amount" : 2000,
    "columns": ["id","id_voluntario","id_tarea","puntaje","flg_invitado","flg_participa"],
    "columnsType": ["txt","dep","dep","rank","rnd","rnd"],
    "data" : {
    	"id":"N",
    	"id_voluntario":{"tableName":"voluntario","columnId":0}, 
    	"id_tarea":{"tableName":"tarea","columnId":0},
    	"puntaje":"RA",
    	"flg_invitado":[1,2],
    	"flg_participa":[1,2]
    	}
	}
]
dbName = "voluntariadodb"
swDelete = 'n'  
swInsert = 'y' 
main(tables, dbName, swDelete, swInsert)
