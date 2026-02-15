'use client';

import { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { recommendationApi } from '@/lib/api';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

const DISH_CATEGORIES = [
  { value: 'BIRYANI', label: '🍛 Biryani' },
  { value: 'MOMOS', label: '🥟 Momos' },
  { value: 'STREET_FOOD', label: '🍢 Street Food' },
  { value: 'FINE_DINING', label: '🍽️ Fine Dining' },
  { value: 'DESSERT', label: '🍰 Dessert' },
  { value: 'BREAKFAST', label: '🍳 Breakfast' },
  { value: 'KEBABS', label: '🍖 Kebabs' },
  { value: 'CHINESE', label: '🥢 Chinese' },
  { value: 'SOUTH_INDIAN', label: '🥞 South Indian' },
  { value: 'OTHER', label: '🍴 Other' },
];

const PRICE_RANGES = [
  { value: 'BUDGET', label: '₹ Budget (< ₹200)' },
  { value: 'MID', label: '₹₹ Mid-Range (₹200-500)' },
  { value: 'PREMIUM', label: '₹₹₹ Premium (₹500-1000)' },
  { value: 'LUXURY', label: '₹₹₹₹ Luxury (> ₹1000)' },
];

const CUISINE_TYPES = [
  'NORTH_INDIAN',
  'SOUTH_INDIAN',
  'CHINESE',
  'MUGHLAI',
  'STREET_FOOD',
  'CONTINENTAL',
  'FAST_FOOD',
  'OTHER',
];

const MEAL_TYPES = ['BREAKFAST', 'LUNCH', 'DINNER', 'SNACK', 'BEVERAGE'];

export default function AddRecommendationPage() {
  const router = useRouter();
  const queryClient = useQueryClient();

  const [formData, setFormData] = useState({
    dishName: '',
    restaurantName: '',
    area: '',
    fullAddress: '',
    googleMapsUrl: '',
    creatorQuote: '',
    description: '',
    dishCategory: '',
    cuisineType: '',
    mealType: '',
    priceRange: '',
    tags: '',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const createMutation = useMutation({
    mutationFn: recommendationApi.create,
    onSuccess: () => {
      setSuccess(true);
      queryClient.invalidateQueries({ queryKey: ['myRecommendations'] });
      
      // Reset form
      setFormData({
        dishName: '',
        restaurantName: '',
        area: '',
        fullAddress: '',
        googleMapsUrl: '',
        creatorQuote: '',
        description: '',
        dishCategory: '',
        cuisineType: '',
        mealType: '',
        priceRange: '',
        tags: '',
      });

      // Redirect after 2 seconds
      setTimeout(() => {
        router.push('/dashboard');
      }, 2000);
    },
    onError: (error: any) => {
      setError(error.response?.data?.message || 'Failed to create recommendation. Please try again.');
    },
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess(false);

    // Convert tags from comma-separated string to array
    const tagsArray = formData.tags
      ? formData.tags.split(',').map((tag) => tag.trim()).filter(Boolean)
      : [];

    const payload = {
      dishName: formData.dishName,
      restaurantName: formData.restaurantName,
      area: formData.area || undefined,
      fullAddress: formData.fullAddress || undefined,
      googleMapsUrl: formData.googleMapsUrl || undefined,
      creatorQuote: formData.creatorQuote || undefined,
      description: formData.description || undefined,
      dishCategory: formData.dishCategory || undefined,
      cuisineType: formData.cuisineType || undefined,
      mealType: formData.mealType || undefined,
      priceRange: formData.priceRange || undefined,
      tags: tagsArray.length > 0 ? tagsArray : undefined,
    };

    createMutation.mutate(payload);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center gap-4">
            <Link href="/dashboard" className="text-gray-600 hover:text-gray-900">
              ← Back to Dashboard
            </Link>
            <h1 className="text-2xl font-bold text-gray-900">Add New Recommendation</h1>
          </div>
        </div>
      </header>

      <div className="container mx-auto px-4 py-8 max-w-3xl">
        {/* Success Message */}
        {success && (
          <div className="mb-6 bg-green-50 border border-green-200 text-green-800 px-4 py-3 rounded-lg">
            ✓ Recommendation created successfully! Redirecting to dashboard...
          </div>
        )}

        {/* Error Message */}
        {error && (
          <div className="mb-6 bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
            {error}
          </div>
        )}

        {/* Form */}
        <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow-md p-6 space-y-6">
          {/* Required Fields Section */}
          <div>
            <h2 className="text-lg font-semibold text-gray-900 mb-4 flex items-center gap-2">
              <span className="text-red-500">*</span> Required Information
            </h2>

            <div className="space-y-4">
              {/* Dish Name */}
              <div>
                <label htmlFor="dishName" className="block text-sm font-medium text-gray-700 mb-1">
                  Dish Name <span className="text-red-500">*</span>
                </label>
                <input
                  id="dishName"
                  name="dishName"
                  type="text"
                  required
                  value={formData.dishName}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="Butter Chicken"
                />
              </div>

              {/* Restaurant Name */}
              <div>
                <label htmlFor="restaurantName" className="block text-sm font-medium text-gray-700 mb-1">
                  Restaurant Name <span className="text-red-500">*</span>
                </label>
                <input
                  id="restaurantName"
                  name="restaurantName"
                  type="text"
                  required
                  value={formData.restaurantName}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="Moti Mahal"
                />
              </div>

              {/* Area */}
              <div>
                <label htmlFor="area" className="block text-sm font-medium text-gray-700 mb-1">
                  Area / Location
                </label>
                <input
                  id="area"
                  name="area"
                  type="text"
                  value={formData.area}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="Daryaganj, Old Delhi"
                />
              </div>
            </div>
          </div>

          {/* Location Details */}
          <div>
            <h2 className="text-lg font-semibold text-gray-900 mb-4">📍 Location Details</h2>

            <div className="space-y-4">
              {/* Full Address */}
              <div>
                <label htmlFor="fullAddress" className="block text-sm font-medium text-gray-700 mb-1">
                  Full Address
                </label>
                <textarea
                  id="fullAddress"
                  name="fullAddress"
                  rows={2}
                  value={formData.fullAddress}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="3703, Netaji Subhash Marg, Daryaganj, New Delhi"
                />
              </div>

              {/* Google Maps URL */}
              <div>
                <label htmlFor="googleMapsUrl" className="block text-sm font-medium text-gray-700 mb-1">
                  Google Maps URL
                </label>
                <input
                  id="googleMapsUrl"
                  name="googleMapsUrl"
                  type="url"
                  value={formData.googleMapsUrl}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="https://maps.google.com/?cid=..."
                />
                <p className="mt-1 text-xs text-gray-500">
                  Tip: Open Google Maps, find the restaurant, click Share, and paste the link here
                </p>
              </div>
            </div>
          </div>

          {/* Your Thoughts */}
          <div>
            <h2 className="text-lg font-semibold text-gray-900 mb-4">💭 Your Thoughts</h2>

            <div className="space-y-4">
              {/* Creator Quote */}
              <div>
                <label htmlFor="creatorQuote" className="block text-sm font-medium text-gray-700 mb-1">
                  Your Quote / Review
                </label>
                <textarea
                  id="creatorQuote"
                  name="creatorQuote"
                  rows={2}
                  value={formData.creatorQuote}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="Best butter chicken in Delhi! Been coming here for years."
                  maxLength={200}
                />
                <p className="mt-1 text-xs text-gray-500">{formData.creatorQuote.length}/200 characters</p>
              </div>

              {/* Description */}
              <div>
                <label htmlFor="description" className="block text-sm font-medium text-gray-700 mb-1">
                  Detailed Description (Optional)
                </label>
                <textarea
                  id="description"
                  name="description"
                  rows={3}
                  value={formData.description}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                  placeholder="The butter chicken here is rich and creamy with the perfect balance of spices..."
                />
              </div>
            </div>
          </div>

          {/* Categorization */}
          <div>
            <h2 className="text-lg font-semibold text-gray-900 mb-4">🏷️ Categorization</h2>

            <div className="grid md:grid-cols-2 gap-4">
              {/* Dish Category */}
              <div>
                <label htmlFor="dishCategory" className="block text-sm font-medium text-gray-700 mb-1">
                  Dish Category
                </label>
                <select
                  id="dishCategory"
                  name="dishCategory"
                  value={formData.dishCategory}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                >
                  <option value="">Select category...</option>
                  {DISH_CATEGORIES.map((cat) => (
                    <option key={cat.value} value={cat.value}>
                      {cat.label}
                    </option>
                  ))}
                </select>
              </div>

              {/* Price Range */}
              <div>
                <label htmlFor="priceRange" className="block text-sm font-medium text-gray-700 mb-1">
                  Price Range
                </label>
                <select
                  id="priceRange"
                  name="priceRange"
                  value={formData.priceRange}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                >
                  <option value="">Select price range...</option>
                  {PRICE_RANGES.map((range) => (
                    <option key={range.value} value={range.value}>
                      {range.label}
                    </option>
                  ))}
                </select>
              </div>

              {/* Cuisine Type */}
              <div>
                <label htmlFor="cuisineType" className="block text-sm font-medium text-gray-700 mb-1">
                  Cuisine Type
                </label>
                <select
                  id="cuisineType"
                  name="cuisineType"
                  value={formData.cuisineType}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                >
                  <option value="">Select cuisine...</option>
                  {CUISINE_TYPES.map((type) => (
                    <option key={type} value={type}>
                      {type.replace('_', ' ')}
                    </option>
                  ))}
                </select>
              </div>

              {/* Meal Type */}
              <div>
                <label htmlFor="mealType" className="block text-sm font-medium text-gray-700 mb-1">
                  Meal Type
                </label>
                <select
                  id="mealType"
                  name="mealType"
                  value={formData.mealType}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
                >
                  <option value="">Select meal type...</option>
                  {MEAL_TYPES.map((type) => (
                    <option key={type} value={type}>
                      {type}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>

          {/* Tags */}
          <div>
            <label htmlFor="tags" className="block text-sm font-medium text-gray-700 mb-1">
              Tags (comma-separated)
            </label>
            <input
              id="tags"
              name="tags"
              type="text"
              value={formData.tags}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
              placeholder="vegetarian, spicy, must-try, family-friendly"
            />
            <p className="mt-1 text-xs text-gray-500">
              Add tags to help people find your recommendation (e.g., vegetarian, non-veg, spicy, must-try)
            </p>
          </div>

          {/* Submit Buttons */}
          <div className="flex gap-4 pt-4">
            <button
              type="submit"
              disabled={createMutation.isPending}
              className="flex-1 py-3 px-4 bg-instagram-gradient text-white rounded-lg font-medium hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-pink-500 disabled:opacity-50"
            >
              {createMutation.isPending ? (
                <span className="flex items-center justify-center">
                  <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Creating...
                </span>
              ) : (
                'Create Recommendation'
              )}
            </button>
            <Link
              href="/dashboard"
              className="px-6 py-3 border border-gray-300 text-gray-700 rounded-lg font-medium hover:bg-gray-50"
            >
              Cancel
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
