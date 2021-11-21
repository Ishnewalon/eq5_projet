import {swalErr, toast} from "../utility";
import {createOffer, getAllOffersByDepartment} from "./offer-service";
import OfferDTO from "../models/OfferDTO";
import {cleanup} from "@testing-library/react";

const offer = new OfferDTO("title", "description", "informatique", "imageUrl", 11, "monitor@email.com", "3 semaines", null, null, "Lundi au Vendredi", 35, 3)
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

const mockFetch = (status, body) => {
    global.fetch = jest.fn(() =>
        Promise.resolve({
            status: status,
            json: () => Promise.resolve(body)
        }));
}

describe("createOffer", () => {
    // noinspection JSValidateTypes
    swalErr.fire = jest.fn(() => Promise.resolve())

    test('Status 201', async () => {
        let body = {message: 'Offer created successfully'}
        mockFetch(201, body)
        // noinspection JSValidateTypes
        toast.fire = jest.fn(() => Promise.resolve())

        let returnedValue = await createOffer(offer);

        expect(returnedValue).toBe(body)
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
test.todo('getAllOffersInvalid')
test.todo('getAllOffersValid')
test.todo('validateOffer')
