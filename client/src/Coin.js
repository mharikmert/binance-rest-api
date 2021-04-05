import './index.css'
const Coin = props =>{
    return <div className = 'coin-field'>
        {props.name} {props.price}
    </div>
}
export default Coin;
