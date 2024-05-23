import axios from 'axios';

const API_URL = 'http://localhost:8080/api/customer';

class CustomerService {
    getCustomers() {
        return axios.get(API_URL);
    }

    getCities() {
        return axios.get(`${API_URL}/cities/-1`);
    }

    getDistricts(cityId) {
        return axios.get(`${API_URL}/districts/${cityId}`);
    }

    getCustomerCount(customerId){
        return axios.get(`${API_URL}/amount/${customerId}`);
    }

    saveCustomer(customer) {
        return axios.post(API_URL, customer);
    }

    deleteCustomer(customerId) {
        return axios.delete(`${API_URL}/${customerId}`);
    }
}
const customerServiceInstance = new CustomerService();
export default customerServiceInstance;
