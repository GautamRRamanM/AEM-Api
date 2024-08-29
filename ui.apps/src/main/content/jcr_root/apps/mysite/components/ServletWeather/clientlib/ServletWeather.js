document.addEventListener("DOMContentLoaded",function(){
document.getElementById("weather1").addEventListener('click',function(e){
    e.preventDefault();
     
    //user input......
    let lat=document.getElementById("lat").value    
    console.log(lat);
    let lon=document.getElementById("lon").value
    console.log(lon);

    if(lat&&lon){
      let url=`http://localhost:4502/bin/raman/Api?lat=${lat}&lon=${lon}`;

      fetch(url)
      .then((res)=>{
        console.log("api res",res);
        return res.json();
        })
        .then((data)=>{
       console.log('====================================');
       console.log("waeth data",data);
       console.log('====================================');
      //for temp
       let tempElement=document.getElementById("temp")
       if(tempElement){
        tempElement.innerText=`${data.temp}°C`;
        console.log(tempElement);
        }else{
            console.log("no temperature updated");
        }


        //for humid....
        let humidElement=document.getElementById("humid")
        if(humidElement){
        humidElement.innerText=`${data.humidity}ρv`;
        console.log(humidElement);
        }else{
            console.log("no humidElement updated");
        }
        //for place....
        let placeElement=document.getElementById("placeName")
        if(placeElement){
            placeElement.innerText=`${data.name}`;
        console.log(placeElement);
        }else{
            console.log("no placeElement updated");
        }
        //for desc....
        let descElement=document.getElementById("desc")
        if(descElement){
            descElement.innerText=`${data.description}`;
        console.log(descElement);
        }else{
            console.log("no descElement updated");
        }
        //for pressure
        let pressurElement=document.getElementById("pressur")
        if(pressurElement){
            pressurElement.innerText=`${data.pressure}pa`;
        console.log(pressurElement);
        }else{
            console.log("no pressurElement updated");
        }

    //for country
        let countryElement=document.getElementById("country")
        if(countryElement){
            countryElement.innerText=`${data.country}`;
        console.log(countryElement);
        }else{
            console.log("no country updated");
        }

        //for pressure
        let sunriseElement=document.getElementById("sunrise")
        if(sunriseElement){
            sunriseElement.innerText=`${data.sunrise}AM`;
        console.log(sunriseElement);
        }else{
            console.log("no sunriseElement updated");
        }

        //for pressure
        let sunsetElement=document.getElementById("sunset")
        if(sunsetElement){
            sunsetElement.innerText=`${data.sunset}PM`;
        console.log(sunsetElement);
        }else{
            console.log("no sunsetElement updated");
        }
        //for pressure
        let icon=document.getElementById("icon2")
        if(icon){
            icon.src=`http://openweathermap.org/img/w/${data.icon}.png`;
        console.log(icon);
        }else{
            console.log("no icon updated");
        }

        })

    }
})
})