import requests
import sys

source_address = sys.argv[1]
dest_address = sys.argv[2]
mode = sys.argv[3]
name= sys.argv[4]
event_date = sys.argv[5]
'''
source_address = "Kerby Street,Arlington,Texas"
dest_address = "East Randol Mill Rd,Arlington,Texas"
mode = "car"
'''
#print(source_address)
#print(dest_address)
#print(mode)

weather_url = requests.get("http://api.openweathermap.org/data/2.5/weather?q="+source_address.split(",")[len(source_address.split(","))-2]+","+source_address.split(",")[len(source_address.split(","))-1]+"&units=metric&appid=YOUR_API_KEY")
weather_result = weather_url.json()
weather_main = weather_result['weather'][0]['main']
#print(weather_result['weather'][0]['main'])
#print(weather_main)
weather_string = ""
if weather_main == "Drizzle" or weather_main == "Rain" or weather_main == "Snow" or weather_main == "Fog" or weather_main == "Tornado" or weather_main == "Mist":
    weather_string = "There might be "+weather_main+". Kindly take precautions."
else:
    weather_string = "The weather is "+weather_main+"."
source_query = ""
source_split = source_address.split(",")
for i in range(len(source_split)):
    source_split1 = source_split[i].split(" ")
    if i == 0:
        for i1 in range(len(source_split1)-1):
            source_query = source_query + source_split1[i1]+"+"
        source_query = source_query + source_split1[len(source_split1)-1]
    else:
        for i1 in range(len(source_split1)):
            source_query = source_query+"+"+source_split1[i1]

destination_query = ""
destination_split = dest_address.split(",")
for i in range(len(destination_split)):
    destination_split1 = destination_split[i].split(" ")
    if i == 0:
        for i1 in range(len(destination_split1)-1):
            destination_query = destination_query + destination_split1[i1]+"+"
        destination_query = destination_query + destination_split1[len(destination_split1)-1]
    else:
        for i1 in range(len(destination_split1)):
            destination_query = destination_query+"+"+destination_split1[i1]

#print(source_query)

#print(destination_query)
geocoding_url1 = requests.get("https://geocoder.ls.hereapi.com/6.2/geocode.json?apiKey=YOUR_API_KEY&searchtext="+source_query)
geocoding_result1 = geocoding_url1.json()
geocoding_coord1 = geocoding_result1['Response']['View'][0]['Result'][0]['Location']['DisplayPosition']
geocoding_bb1 = geocoding_result1['Response']['View'][0]['Result'][0]['Location']['MapView']
#print(geocoding_result['Response']['View'][0]['Result'][0]['Location']['DisplayPosition'])

geocoding_url2 = requests.get("https://geocoder.ls.hereapi.com/6.2/geocode.json?apiKey=YOUR_API_KEY&searchtext="+destination_query)
geocoding_result2 = geocoding_url2.json()
geocoding_coord2 = geocoding_result2['Response']['View'][0]['Result'][0]['Location']['DisplayPosition']
geocoding_bb2 = geocoding_result2['Response']['View'][0]['Result'][0]['Location']['MapView']

traffic_url1 = requests.get("https://traffic.ls.hereapi.com/traffic/6.2/flow.json?apiKey=YOUR_API_KEY&bbox="+str(geocoding_bb1['TopLeft']['Latitude'])+","+str(geocoding_bb1['TopLeft']['Longitude'])+";"+str(geocoding_bb1['TopLeft']['Latitude'])+","+str(geocoding_bb1['TopLeft']['Longitude']))
traffic_info1 = traffic_url1.json()
total_jf = 0
jf_cnt = 0
traffic_nav1 = traffic_info1['RWS'][0]['RW']
for i in range(len(traffic_nav1)):
    traffic_nav11 = traffic_nav1[i]['FIS'][0]['FI']
    for i1 in range(len(traffic_nav11)):
        total_jf = total_jf + traffic_nav11[i1]['CF'][0]['JF']
        jf_cnt = jf_cnt + 1
traffic_string = ""
average_jf = total_jf/jf_cnt
if average_jf >= 0.0 and average_jf < 4.0:
    traffic_string = "Traffic might be less."
elif average_jf >= 4.0 and average_jf < 8.0:
    traffic_string = "Traffic might be medium."
else:
    traffic_string = "Traffic might be high. Be careful and plan accordingly."
'''
print("total_jf "+total_jf)
print("jf_cnt "+jf_cnt)
'''

routing_url = requests.get("https://route.ls.hereapi.com/routing/7.2/calculateroute.json?apiKey=YOUR_API_KEY&waypoint0=geo!"+str(geocoding_coord1['Latitude'])+","+str(geocoding_coord1['Longitude'])+"&waypoint1=geo!"+str(geocoding_coord2['Latitude'])+","+str(geocoding_coord2['Longitude'])+"&mode=fastest;"+mode+";traffic:enabled")
routing_result = routing_url.json()
routing_summary = routing_result['response']['route'][0]['summary']
#print(routing_summary)
route_distance = routing_summary['distance']/1000;
route_time = routing_summary['trafficTime']/60;
routing_string = "Total distance is: "+str(route_distance)+" and it takes "+str(route_time)+" minutes to travel."

total_summary = "Regarding your event "+name+" on "+event_date+": "+routing_string+" "+weather_string+" "+traffic_string
print(total_summary)
sys.exit("Success")
