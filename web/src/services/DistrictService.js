import axios from 'axios';

const API_URL = 'http://localhost:8080/api/district';

class DistrictService {
    getAllDistricts() {
        return axios.get(API_URL);
    }

    getDistrictByCode(code) {
        return axios.get(`${API_URL}/code/${code}`);
    }

    getDistrictCount(districtId){
        return axios.get(`${API_URL}/amount/${districtId}`);
    }

    getDistrictsOfCityCount(code){
        return axios.get(`${API_URL}/city/amount/${code}`);
    }

    saveCity(city) {
        return axios.post(API_URL, city);
    }

    saveDistrict(district) {
        return axios.post(API_URL, district);
    }

    deleteDistrict(id) {
        return axios.delete(`${API_URL}/${id}`);
    }
}

const districtServiceInstance = new DistrictService();
export default districtServiceInstance;
