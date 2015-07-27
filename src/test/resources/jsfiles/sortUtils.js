

////////////////////
function sortRestaurantsByState(restsaurantString)
{
    
    var restaurantObj =  JSON3.parse(restsaurantString);
    var sortedRestaurants = {};
    sortedRestaurants['sample'] = [];
    
    
    sortedRestaurants.sample = restaurantObj.sample.sort(function (a,b)
    {
        
        return a.state.localeCompare(b.state);
        
    });
    
    return JSON3.stringify(sortedRestaurants);
}

 