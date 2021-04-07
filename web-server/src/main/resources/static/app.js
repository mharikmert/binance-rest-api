

let url = "https://api.binance.com/api/v3/ticker/price?symbol=";
const showData = async (url,coin) => {

    const response = await fetch(url + coin + 'BTC');
    const data = await response.json();
    console.log(data);
    document.querySelector('#' + coin).textContent = data['price'];

    postData('api/v1/subCoins', data)
    .then(data => console.log(JSON.stringify(data)))
    .catch(error => console.log(error))

    //time period for data flow
    setTimeout( () => showData(url,coin),1000);
}

const postData = (url = '', data = {}) => {
    //post request
    return fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data),
    }).then(response => response.json());
}
//showData(url, 'USDT')
//showData(url, 'TRY')
showData(url, 'ETH')
showData(url, 'BNB')
showData(url, 'XRP')
showData(url, 'BCH')
showData(url, 'LTC')
