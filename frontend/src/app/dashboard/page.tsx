'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { useQuery, useMutation } from '@tanstack/react-query';
import { recommendationApi } from '@/lib/api';
import Link from 'next/link';

interface Creator {
  id: number;
  username: string;
  displayName: string;
  slug: string;
  email: string;
  totalRecommendations: number;
  totalPageViews: number;
}

export default function DashboardPage() {
  const router = useRouter();
  const [creator, setCreator] = useState<Creator | null>(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const storedCreator = localStorage.getItem('creator');

    if (!token || !storedCreator) {
      router.push('/auth/login');
      return;
    }

    setCreator(JSON.parse(storedCreator));
  }, [router]);

  const { data: recommendations, isLoading } = useQuery({
    queryKey: ['myRecommendations'],
    queryFn: () => recommendationApi.getMyRecommendations(),
    enabled: !!creator,
  });

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('creator');
    router.push('/');
  };

  if (!creator) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-pink-500"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Creator Dashboard</h1>
            <p className="text-sm text-gray-600">Welcome back, {creator.displayName}!</p>
          </div>
          <div className="flex gap-4">
            <Link
              href={`/${creator.slug}`}
              target="_blank"
              className="px-4 py-2 text-sm border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              View Public Page →
            </Link>
            <button
              onClick={handleLogout}
              className="px-4 py-2 text-sm text-red-600 border border-red-300 rounded-lg hover:bg-red-50"
            >
              Logout
            </button>
          </div>
        </div>
      </header>

      <div className="container mx-auto px-4 py-8">
        {/* Stats Cards */}
        <div className="grid md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center justify-between mb-2">
              <h3 className="text-gray-600 text-sm font-medium">Total Recommendations</h3>
              <span className="text-2xl">🍽️</span>
            </div>
            <p className="text-3xl font-bold text-gray-900">{creator.totalRecommendations}</p>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center justify-between mb-2">
              <h3 className="text-gray-600 text-sm font-medium">Page Views</h3>
              <span className="text-2xl">👁️</span>
            </div>
            <p className="text-3xl font-bold text-gray-900">{creator.totalPageViews}</p>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center justify-between mb-2">
              <h3 className="text-gray-600 text-sm font-medium">Your Page</h3>
              <span className="text-2xl">🔗</span>
            </div>
            <Link
              href={`/${creator.slug}`}
              target="_blank"
              className="text-lg font-semibold text-pink-600 hover:underline break-all"
            >
              /{creator.slug}
            </Link>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <h2 className="text-xl font-bold text-gray-900 mb-4">Quick Actions</h2>
          <div className="grid md:grid-cols-2 gap-4">
            <Link
              href="/dashboard/add-recommendation"
              className="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-pink-500 hover:bg-pink-50 transition text-left"
            >
              <div className="flex items-center gap-3">
                <span className="text-3xl">➕</span>
                <div>
                  <h3 className="font-semibold text-gray-900">Add Recommendation</h3>
                  <p className="text-sm text-gray-600">Create a new food recommendation</p>
                </div>
              </div>
            </Link>

            <button className="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-pink-500 hover:bg-pink-50 transition text-left">
              <div className="flex items-center gap-3">
                <span className="text-3xl">📊</span>
                <div>
                  <h3 className="font-semibold text-gray-900">View Analytics</h3>
                  <p className="text-sm text-gray-600">See your performance metrics</p>
                </div>
              </div>
            </button>
          </div>
        </div>

        {/* Recommendations List */}
        <div className="bg-white rounded-lg shadow">
          <div className="p-6 border-b border-gray-200">
            <h2 className="text-xl font-bold text-gray-900">Your Recommendations</h2>
          </div>

          <div className="p-6">
            {isLoading ? (
              <div className="text-center py-12">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-pink-500 mx-auto"></div>
              </div>
            ) : recommendations && recommendations.length > 0 ? (
              <div className="space-y-4">
                {recommendations.map((rec: any) => (
                  <div
                    key={rec.id}
                    className="border border-gray-200 rounded-lg p-4 hover:border-pink-300 transition"
                  >
                    <div className="flex items-start justify-between">
                      <div className="flex-1">
                        <h3 className="font-semibold text-lg text-gray-900">
                          {rec.dishName}
                        </h3>
                        <p className="text-gray-600">{rec.restaurantName}</p>
                        <p className="text-sm text-gray-500">📍 {rec.area}</p>
                        {rec.creatorQuote && (
                          <p className="text-sm text-gray-600 italic mt-2">
                            "{rec.creatorQuote}"
                          </p>
                        )}
                      </div>
                      <div className="text-right">
                        <div className="text-sm text-gray-500 space-y-1">
                          <p>👁️ {rec.viewCount} views</p>
                          <p>📱 {rec.instagramClicks} clicks</p>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <p className="text-4xl mb-4">🍽️</p>
                <p className="text-xl text-gray-600 mb-2">No recommendations yet</p>
                <p className="text-gray-500 mb-6">
                  Start by creating your first food recommendation
                </p>
                <Link
                  href="/dashboard/add-recommendation"
                  className="inline-block px-6 py-2 bg-instagram-gradient text-white rounded-lg hover:opacity-90"
                >
                  Add Your First Recommendation
                </Link>
              </div>
            )}
          </div>
        </div>

        {/* Tips */}
        <div className="mt-8 bg-blue-50 border border-blue-200 rounded-lg p-6">
          <h3 className="font-semibold text-blue-900 mb-2">💡 Quick Tips</h3>
          <ul className="text-sm text-blue-800 space-y-1">
            <li>• Share your page link in your Instagram bio</li>
            <li>• Add detailed quotes to make recommendations more engaging</li>
            <li>• Include Google Maps links so viewers can easily find places</li>
            <li>• Update regularly to keep your audience engaged</li>
          </ul>
        </div>
      </div>
    </div>
  );
}
