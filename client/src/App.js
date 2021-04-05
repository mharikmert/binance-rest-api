import Coin from './Coin'
import './index.css'
const App = () => {
    const getData = (url, coin) => {
        url += coin + 'BTC';
        const xhr = new XMLHttpRequest();
        xhr.open('GET',url, false);
        xhr.send();
        if(xhr.status === 200)
                return JSON.parse(xhr.responseText)['price'];
    }
    const url = 'https://api.binance.com/api/v3/ticker/price?symbol=';
    return (
        <div className = 'container'>
            <Coin name = 'BNB' price = {getData(url,'BNB')}> </Coin>
            <Coin name = 'ETH' price = {getData(url, 'ETH')}> </Coin>
            <Coin name = 'XRP' price = {getData(url, 'XRP')}> </Coin>
            <Coin name = 'BCH' price = {getData(url, 'BCH')}> </Coin>
            <Coin name = 'LTC' price = {getData(url, 'LTC')}> </Coin>
        </div>)
}

export default App;
