import {swalErr, toast} from "../utility";
import {
    createOffer,
    getAllOffersByDepartment,
    getAllOffersInvalid,
    getAllOffersValid,
    validateOffer
} from "./offer-service";
import OfferDTO from "../models/OfferDTO";
import {cleanup} from "@testing-library/react";

const offer = new OfferDTO("title", "description", "informatique", "imageUrl", 11, "monitor@email.com", "3 semaines", null, null, "Lundi au Vendredi", 35, 3)
const mockFetch = (status, body) => {
    global.fetch = jest.fn(() =>
        Promise.resolve({
            status: status,
            json: () => Promise.resolve(body)
        }));
}
jest.mock('../utility')

beforeAll(() => {
    jest.spyOn(global.console, 'error').mockImplementation(() => {
    });
});

afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})

afterAll(() => {
    global.console.error.mockRestore();
});


describe("createOffer", () => {
    // noinspection JSValidateTypes
    swalErr.fire = jest.fn(() => Promise.resolve())

    test('Status 201', async () => {
        let body = {message: 'Offer created successfully'}
        mockFetch(201, body)
        // noinspection JSValidateTypes
        toast.fire = jest.fn(() => Promise.resolve())

        let returnedValue = await createOffer(offer);

        expect(returnedValue).toEqual(body)
        expect(toast.fire).toHaveBeenCalled()
    });

    test('Status 400', async () => {
        let badRequestMessage = 'BadRequest message';
        mockFetch(400, {
            message: badRequestMessage
        })

        let returnedValue = await createOffer(offer);

        expect(returnedValue).toBeNull()
        expect(swalErr.fire).toHaveBeenCalledWith({text: badRequestMessage})
    });

    test('Status other', async () => {
        mockFetch(404, {
            message: 'BadRequest message'
        })

        let returnedValue = await createOffer(offer);

        expect(returnedValue).toBeNull()
        expect(swalErr.fire).not.toHaveBeenCalled()
    });
})
describe('getAllOffersByDepartment', () => {

    test('Status 200', async () => {
        const body = [offer, offer, offer]
        mockFetch(200, body)

        let returnedValue = await getAllOffersByDepartment('informatique')

        expect(returnedValue).toEqual(body)
        expect(console.error).not.toHaveBeenCalled()
    })

    test('Status other', async () => {
        mockFetch(400, {message: 'BadRequest message'})

        let returnedValue = await getAllOffersByDepartment('informatique')

        expect(returnedValue).toEqual([])
        expect(console.error).toHaveBeenCalled()
    })
})
describe('getAllOffersInvalid', () => {

    test('Status 200', async () => {
        const body = [offer, offer, offer]
        mockFetch(200, body)

        let returnedValue = await getAllOffersInvalid()

        expect(returnedValue).toEqual(body)
        expect(console.error).not.toHaveBeenCalled()
    })

    test('Status other', async () => {
        mockFetch(400, {message: 'BadRequest message'})

        let returnedValue = await getAllOffersInvalid()

        expect(returnedValue).toEqual([])
        expect(console.error).toHaveBeenCalled()
    })
})
describe('getAllOffersValid', () => {

    test('Status 200', async () => {
        const body = [offer, offer, offer]
        mockFetch(200, body)

        let returnedValue = await getAllOffersValid()

        expect(returnedValue).toEqual(body)
        expect(console.error).not.toHaveBeenCalled()
    })

    test('Status other', async () => {
        mockFetch(400, {message: 'BadRequest message'})

        let returnedValue = await getAllOffersValid()

        expect(returnedValue).toEqual([])
        expect(console.error).toHaveBeenCalled()
    })
})
test.todo('validateOffer')
describe("validateOffer", () => {
    // noinspection JSValidateTypes
    toast.fire = jest.fn(() => Promise.resolve())
    // noinspection JSValidateTypes
    swalErr.fire = jest.fn(() => Promise.resolve())

    test('Status 200 valid', async () => {
        mockFetch(200, null)

        await validateOffer(1, true);

        expect(toast.fire).toHaveBeenCalledWith({title: "Offre validée!"})
    });
    test('Status 200 invalid', async () => {
        mockFetch(200, null)

        await validateOffer(1, false);

        expect(toast.fire).toHaveBeenCalledWith({title: "Offre invalidée!"})
    });

    test('Status 400', async () => {
        let badRequestMessage = 'BadRequest message';
        mockFetch(400, {
            message: badRequestMessage
        })

        await validateOffer(1, true);

        expect(swalErr.fire).toHaveBeenCalledWith({text: badRequestMessage})
    });

    test('Status other', async () => {
        mockFetch(404, {
            message: 'BadRequest message'
        })

        await validateOffer(offer);

        expect(swalErr.fire).not.toHaveBeenCalled()
    });
})
